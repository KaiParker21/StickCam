package com.skye.stickcam.camera

import android.graphics.Bitmap
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.scale
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
    isFrontCamera: Boolean,
    onFrameCaptured: (Bitmap) -> Unit,
    onPhotoCaptured: (Bitmap) -> Unit,
    capturePhotoTrigger: MutableState<Boolean>
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(
            if(isFrontCamera) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
        )
        .build()

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = modifier,
        update = { previewView ->

            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(256, 256))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(executor) { imageProxy->
                try {
                    val bitmap = imageProxy.toBitmap()
                    val resizedBitmap = bitmap.scale(256, 256)
                    onFrameCaptured(resizedBitmap)

                    if(capturePhotoTrigger.value) {
                        onPhotoCaptured(bitmap)
                        capturePhotoTrigger.value = false
                    }
                } catch (e: IllegalArgumentException) {
                    Log.e("CameraPreview", "Unsupported Image format", e)
                } catch (e: UnsupportedOperationException) {
                    Log.e("CameraPreview", "Failed to convert ImageProxy to Bitmap", e)
                } finally {
                    imageProxy.close()
                }
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }
    )
}