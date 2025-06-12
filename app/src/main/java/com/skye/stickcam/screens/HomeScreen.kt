package com.skye.stickcam.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.media.AudioManager
import android.media.MediaActionSound
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.skye.stickcam.camera.CameraPreview
import com.skye.stickcam.camera.PoseEstimator
import com.skye.stickcam.components.CircleButton
import com.skye.stickcam.components.GeneralSettingsDialog
import com.skye.stickcam.components.SettingsDialog
import com.skye.stickcam.data.PoseViewModel
import com.skye.stickcam.drawOverlayOnBitmap
import com.skye.stickcam.loadAllSavedImages
import com.skye.stickcam.mirrorBitmapHorizontally
import com.skye.stickcam.rotateBitmap
import com.skye.stickcam.saveBitmapToGallery
import kotlin.math.sqrt


@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedBoxWithConstraintsScope"
)
@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    poseViewModel: PoseViewModel = viewModel(),
    onGalleryButtonClicked: () -> Unit
) {

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val cameraSound = MediaActionSound()
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val keypoints by poseViewModel.keypoints.collectAsState()
    val showStick by poseViewModel.showStick.collectAsState()
    val showPoints by poseViewModel.showPoints.collectAsState()
    val stickColor by poseViewModel.stickColor.collectAsState()
    val stickAlpha by poseViewModel.stickAlpha.collectAsState()
    val stickWidth by poseViewModel.stickWidth.collectAsState()
    val pointColor by poseViewModel.pointColor.collectAsState()
    val pointAlpha by poseViewModel.pointAlpha.collectAsState()
    val pointRadius by poseViewModel.pointRadius.collectAsState()
    val showNumbers by poseViewModel.showNumbers.collectAsState()
    val isFrontCamera by poseViewModel.isFrontCamera.collectAsState()
    val showBackground by poseViewModel.showBackground.collectAsState()
    val backgroundColor by poseViewModel.backgroundColor.collectAsState()

    val connections by poseViewModel.connections.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val poseEstimator = remember { PoseEstimator(context) }
    val capturePhoto = remember { mutableStateOf(false) }
    var refreshTrigger by remember { mutableIntStateOf(0) }

    val latestUri by remember(refreshTrigger) {
        mutableStateOf(loadAllSavedImages(context).firstOrNull())
    }

    var showStickSettings by remember { mutableStateOf(false) }
    var showPointSettings by remember { mutableStateOf(false) }
    var showGeneralSettings by remember { mutableStateOf(false) }
    var isStickSelected by remember { mutableStateOf(false) }
    var isCameraSelected by remember { mutableStateOf(true) }
    var isOutlineSelected by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showStickSettings = !showStickSettings
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timeline,
                                contentDescription = "Stick Settings",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showPointSettings = !showPointSettings
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Watch,
                                contentDescription = "Point Settings",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showGeneralSettings = !showGeneralSettings
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "General Settings",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) {
        
        if(showPointSettings) {
            SettingsDialog(
                slider1Name = "Key Point Radius",
                slider2Name = "Key Point Alpha",
                slider1Value = pointRadius,
                slider2Value = pointAlpha,
                onSlider1ValueChange = { radius->
                    poseViewModel.setPointRadius(radius)
                },
                onSlider2ValueChange = { alpha->
                    poseViewModel.setPointAlpha(alpha)
                },
                switch1Name = "Show Key Points",
                switch2Name = "Points Color",
                switch1Value = showPoints,
                onSwitch1ValueChange = {
                    poseViewModel.setShowPoints(it)
                },
                onDismissRequest = {
                    showPointSettings = false
                },
                onColorSelected = { color->
                    poseViewModel.setPointColor(color.toArgb())
                }
            )
        }

        if(showStickSettings) {
            SettingsDialog(
                slider1Name = "Stick Width",
                slider2Name = "Stick Alpha",
                slider1Value = stickWidth,
                slider2Value = stickAlpha,
                onSlider1ValueChange = { width->
                    poseViewModel.setStickWidth(width)
                },
                onSlider2ValueChange = { alpha->
                    poseViewModel.setStickAlpha(alpha)
                },
                switch1Name = "Show Stick lines",
                switch2Name = "Stick Color",
                switch1Value = showStick,
                onSwitch1ValueChange = {
                    poseViewModel.setShowStick(it)
                },
                onDismissRequest = {
                    showStickSettings = false
                },
                onColorSelected = { color->
                    poseViewModel.setStickColor(color.toArgb())

                }
            )
        }

        if(showGeneralSettings) {
            GeneralSettingsDialog(
                switch1Name = "Show Background",
                switch2Name = "Show Numbers",
                switch1Value = showBackground,
                switch2Value = showNumbers,
                onSwitch1ValueChange = {
                    poseViewModel.setShowBackground(it)
                },
                onSwitch2ValueChange = {
                    poseViewModel.setShowNumbers(it)
                },
                onDismissRequest = {
                    showGeneralSettings = false
                },
                onColorSelected = { color ->
                    poseViewModel.setBackgroundColor(color.toArgb())
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        0.18f
                    )
                    .background(
                        color = Color.Black
                    )
            ) { }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(
                        1f
                    )
                    .background(
                        color = Color.Gray
                    )
            ) {

                val previewWidth =
                    constraints.maxWidth.toFloat()
                val previewHeight =
                    constraints.maxHeight.toFloat()

                CameraPreview(
                    onPhotoCaptured = { bitmap ->
                        val rotatedBitmap = rotateBitmap(bitmap, if (isFrontCamera) 270f else 90f)
                        val flippedBitmap = if (isFrontCamera) mirrorBitmapHorizontally(rotatedBitmap) else rotatedBitmap
                        val finalBitmap = drawOverlayOnBitmap(
                            baseBitmap = flippedBitmap,
                            keypoints = keypoints,
                            connections = connections,
                            stickColor = stickColor,
                            stickAlpha = stickAlpha,
                            stickWidth = stickWidth,
                            pointColor = pointColor,
                            pointAlpha = pointAlpha,
                            pointRadius = pointRadius,
                            showStick = showStick,
                            showPoints = showPoints,
                            showNumbers = showNumbers,
                            showBackground = showBackground,
                            backgroundColor = backgroundColor
                        )

                        saveBitmapToGallery(
                            context,
                            finalBitmap,
                            onImageSaved = {
                                refreshTrigger++
                            }
                        )

                    },
                    lifecycleOwner = lifecycleOwner,
                    isFrontCamera = isFrontCamera,
                    modifier = Modifier
                        .fillMaxSize(),
                    onFrameCaptured = { bitmap ->
                        val rotatedBitmap =
                            rotateBitmap(
                                bitmap,
                                if (isFrontCamera) 270f else 90f
                            )
                        val poseResult =
                            poseEstimator.estimatePose(
                                rotatedBitmap
                            )

                        val scaleX =
                            previewWidth / 256f
                        val scaleY =
                            previewHeight / 256f

                        val mappedKeypoints =
                            poseResult.map { (x, y) ->
                                val finalX =
                                    if (isFrontCamera) 256f - x else x
                                val finalY =
                                    y

                                val mappedX =
                                    finalX * scaleX
                                val mappedY =
                                    finalY * scaleY

                                Pair(
                                    mappedX,
                                    mappedY
                                )
                            }
                        poseViewModel.updateKeypoints(
                            mappedKeypoints
                        )
                    },
                    capturePhotoTrigger = capturePhoto
                )

                Canvas(modifier = Modifier.fillMaxSize()) {

                    val canvas = drawContext.canvas.nativeCanvas
                    if(showBackground) {
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
                        typeface = Typeface
                            .create(
                                Typeface.DEFAULT,
                                Typeface.BOLD
                            )
                        isAntiAlias = true
                        setShadowLayer(6f, 2f, 2f, android.graphics.Color.BLACK)
                    }

                    val linePaint = Paint().apply {
                        color = stickColor
                        strokeWidth = stickWidth
                        isAntiAlias = true
                        alpha = stickAlpha.toInt()
                    }

                    if(showStick && !isCameraSelected) {
                        if (keypoints.size >= 7) {
                            val headPoints = listOf(0, 1, 2, 3, 4).map { keypoints[it] }

                            val centerX = headPoints.map { it.first }
                                .average()
                                .toFloat()
                            val centerY = headPoints.map { it.second }
                                .average()
                                .toFloat()

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
                            val midShoulderX =
                                (x5 + x6) / 2f
                            val midShoulderY =
                                (y5 + y6) / 2f

                            canvas.drawLine(
                                centerX,
                                centerY + radius,
                                midShoulderX,
                                midShoulderY,
                                linePaint
                            )
                        }

                        connections.forEach { (start, end) ->
                            if (start < keypoints.size && end < keypoints.size) {
                                val (x1, y1) = keypoints[start]
                                val (x2, y2) = keypoints[end]
                                canvas.drawLine(
                                    x1,
                                    y1,
                                    x2,
                                    y2,
                                    linePaint
                                )
                            }
                        }
                    }

                    keypoints.forEachIndexed { index, (x, y) ->
                        if(showPoints && !isCameraSelected) {
                            canvas.drawCircle(
                                x,
                                y,
                                pointRadius,
                                circlePaint
                            )
                        }
                        if (showNumbers && !isCameraSelected) {
                            canvas.drawText(
                                index.toString(),
                                x + 15f,
                                y - 15f,
                                textPaint
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight(
                        0.3f
                    )
                    .fillMaxWidth()
                    .background(
                        color = Color.Black
                    )
            ) { }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        0.43f
                    )
                    .background(
                        color = Color.Black
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleButton(
                    heightFraction = 0.8f,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onGalleryButtonClicked()
                    },
                    painter = rememberAsyncImagePainter(latestUri)
                )

                CircleButton(
                    heightFraction = 1f,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        if (audioManager.ringerMode == AudioManager.RINGER_MODE_NORMAL) {
                            cameraSound.play(MediaActionSound.SHUTTER_CLICK)
                        }
                        capturePhoto.value = true
                    },
                    color = Color.White
                )

                CircleButton(
                    heightFraction = 0.8f,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        poseViewModel.setIsFrontCamera(!isFrontCamera)
                    },
                    icon = Icons.Default.Cameraswitch
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxHeight(
                        0.9f
                    )
                    .fillMaxWidth()
                    .background(
                        color = Color.Black
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(11.dp))

                OutlinedButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        isStickSelected = true
                        isCameraSelected = false
                        isOutlineSelected = false

                        poseViewModel.setShowBackground(true)
                        poseViewModel.setShowStick(true)
                        poseViewModel.setShowPoints(false)
                        poseViewModel.setShowNumbers(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isStickSelected) Color.White else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Transparent
                    )
                ) {
                    Text(
                        text = " Stick ",
                        color = if(isStickSelected) Color.Black else Color.White
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                OutlinedButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        isStickSelected = false
                        isCameraSelected = true
                        isOutlineSelected = false

                        poseViewModel.setShowBackground(false)
                        poseViewModel.setShowStick(false)
                        poseViewModel.setShowPoints(false)
                        poseViewModel.setShowNumbers(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCameraSelected) Color.White else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Transparent
                    )
                ) {
                    Text(
                        text = "Camera",
                        color = if(isCameraSelected) Color.Black else Color.White
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                OutlinedButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        isStickSelected = false
                        isCameraSelected = false
                        isOutlineSelected = true

                        poseViewModel.setShowBackground(false)
                        poseViewModel.setShowStick(true)
                        poseViewModel.setShowPoints(true)
                        poseViewModel.setShowNumbers(true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isOutlineSelected) Color.White else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Transparent
                    )
                ) {
                    Text(
                        text = "Outline",
                        color = if(isOutlineSelected) Color.Black else Color.White
                    )
                }
            }
        }
    }
}
