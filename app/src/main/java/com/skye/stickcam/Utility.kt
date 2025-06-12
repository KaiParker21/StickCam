package com.skye.stickcam

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sqrt

fun loadAllSavedImages(context: Context): List<Uri> {
    val imageUris = mutableListOf<Uri>()
    val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.RELATIVE_PATH
    )

    val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
    val selectionArgs = arrayOf("%Pictures/StickCam%")

    context.contentResolver.query(
        collection,
        projection,
        selection,
        selectionArgs,
        "${MediaStore.Images.Media.DATE_ADDED} DESC"
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val uri = ContentUris.withAppendedId(collection, id)
            imageUris.add(uri)
        }
    }

    return imageUris
}


fun saveBitmapToGallery(
    context: Context,
    bitmap: Bitmap,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    onImageSaved: () -> Unit
) {
    val filename = "StickCam_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.${if (format == Bitmap.CompressFormat.PNG) "png" else "jpg"}"

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, if (format == Bitmap.CompressFormat.PNG) "image/png" else "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/StickCam")
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        try {
            val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
            outputStream.use { stream ->
                if (stream == null) throw Exception("OutputStream is null")
                bitmap.compress(format, 100, stream)
            }
            onImageSaved()
            Log.d("saveBitmapToGallery", "Saved image to gallery: $filename")
        } catch (e: Exception) {
            Log.e("saveBitmapToGallery", "Error saving image: ${e.message}", e)
        }
    } ?: Log.e("saveBitmapToGallery", "Failed to insert MediaStore record")
}

fun drawOverlayOnBitmap(
    baseBitmap: Bitmap,
    keypoints: List<Pair<Float, Float>>,
    connections: List<Pair<Int, Int>>,
    stickColor: Int,
    stickAlpha: Float,
    stickWidth: Float,
    pointColor: Int,
    pointAlpha: Float,
    pointRadius: Float,
    showStick: Boolean,
    showPoints: Boolean,
    showNumbers: Boolean,
    showBackground: Boolean,
    backgroundColor: Int
): Bitmap {
    val result = baseBitmap.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = android.graphics.Canvas(result)

    if (showBackground) {
        canvas.drawColor(backgroundColor)
    }

    val circlePaint = Paint().apply {
        color = pointColor
        isAntiAlias = true
        style = Paint.Style.FILL
        alpha = pointAlpha.toInt()
    }

    val textPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 40f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
        setShadowLayer(6f, 2f, 2f, android.graphics.Color.BLACK)
    }

    val linePaint = Paint().apply {
        color = stickColor
        strokeWidth = stickWidth
        isAntiAlias = true
        alpha = stickAlpha.toInt()
    }

    if (showStick && keypoints.size >= 7) {
        val headPoints = listOf(0, 1, 2, 3, 4).map { keypoints[it] }
        val centerX = headPoints.map { it.first }.average().toFloat()
        val centerY = headPoints.map { it.second }.average().toFloat()
        val dx = keypoints[3].first - keypoints[4].first
        val dy = keypoints[3].second - keypoints[4].second
        val radius = ((sqrt((dx * dx + dy * dy).toDouble()) / 2.0) * 1.4).toFloat().coerceAtLeast(40f)

        canvas.drawCircle(centerX, centerY, radius, Paint().apply {
            color = stickColor
            style = Paint.Style.STROKE
            strokeWidth = stickWidth
            isAntiAlias = true
            alpha = stickAlpha.toInt()
        })

        val (x5, y5) = keypoints[5]
        val (x6, y6) = keypoints[6]
        val midShoulderX = (x5 + x6) / 2f
        val midShoulderY = (y5 + y6) / 2f

        canvas.drawLine(centerX, centerY + radius, midShoulderX, midShoulderY, linePaint)
    }

    if (showStick) {
        connections.forEach { (start, end) ->
            if (start < keypoints.size && end < keypoints.size) {
                val (x1, y1) = keypoints[start]
                val (x2, y2) = keypoints[end]
                canvas.drawLine(x1, y1, x2, y2, linePaint)
            }
        }
    }

    keypoints.forEachIndexed { index, (x, y) ->
        if (showPoints) {
            canvas.drawCircle(x, y, pointRadius, circlePaint)
        }
        if (showNumbers) {
            canvas.drawText(index.toString(), x + 15f, y - 15f, textPaint)
        }
    }

    return result
}

fun mirrorBitmapHorizontally(bitmap: Bitmap): Bitmap {
    val matrix = Matrix().apply { preScale(-1f, 1f) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
}

fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(rotationDegrees)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

