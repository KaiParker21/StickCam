package com.skye.stickcam.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PoseEstimator(context: Context) {

    private val interpreter: Interpreter
    private val inputSize = 256

    init {
        val assetFileDescriptor = context.assets.openFd("movenet_singlepose_thunder.tflite")
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength

        val modelByteBuffer = fileChannel.map(
            java.nio.channels.FileChannel.MapMode.READ_ONLY,
            startOffset,
            declaredLength
        )

        val options = Interpreter.Options()
        interpreter = Interpreter(modelByteBuffer, options)
        Log.d("PoseEstimator", "Model loaded successfully")

        val inputTensor = interpreter.getInputTensor(0)
        val outputTensor = interpreter.getOutputTensor(0)

        Log.d("ModelInfo", "Input shape: ${inputTensor.shape().joinToString(",")}")
        Log.d("ModelInfo", "Input datatype: ${inputTensor.dataType()}")
        Log.d("ModelInfo", "Output shape: ${outputTensor.shape().joinToString(",")}")
        Log.d("ModelInfo", "Output datatype: ${outputTensor.dataType()}")
    }

    fun estimatePose(bitmap: Bitmap): List<Pair<Float, Float>> {

        val inputBuffer = preprocess(bitmap)

        val output = Array(1) { Array(1) { Array(17) { FloatArray(3) } } }

        interpreter.run(inputBuffer, output)

        return output[0][0].map { keypoint ->
            val y = keypoint[0]
            val x = keypoint[1]
            val score = keypoint[2]
            Pair(x * inputSize, y * inputSize)
        }
    }

    private fun preprocess(bitmap: Bitmap): ByteBuffer {
        val input = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4).apply {
            order(ByteOrder.nativeOrder())
        }

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        val intValues = IntArray(inputSize * inputSize)
        resizedBitmap.getPixels(intValues, 0, inputSize, 0, 0, inputSize, inputSize)

        for (pixel in intValues) {
            val r = ((pixel shr 16) and 0xFF).toFloat()
            val g = ((pixel shr 8) and 0xFF).toFloat()
            val b = (pixel and 0xFF).toFloat()
            input.putFloat(r)
            input.putFloat(g)
            input.putFloat(b)
        }

        input.rewind()
        return input
    }
}

