package com.skye.stickcam.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun ImagePreview(
    imageUri: Uri,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Image Preview",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}