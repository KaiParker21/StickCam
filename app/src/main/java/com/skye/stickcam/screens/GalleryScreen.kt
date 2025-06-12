package com.skye.stickcam.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.skye.stickcam.components.ImagePreview
import com.skye.stickcam.loadAllSavedImages
import com.skye.stickcam.ui.theme.StickCamTheme

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun GalleryScreen(
    onBackButtonClicked: () -> Unit
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var previewUri by remember { mutableStateOf<Uri?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackButtonClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues->

        val context = LocalContext.current
        val imageUris by remember { mutableStateOf(loadAllSavedImages(context)) }

        if(showImagePreview && previewUri != null) {
            ImagePreview(
                imageUri = previewUri!!,
                onDismissRequest = {
                    showImagePreview = false
                }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageUris.size) { index ->
                val uri = imageUris[index]
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = "Captured Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            previewUri = uri
                            showImagePreview = true
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryPreview() {
    StickCamTheme {
        GalleryScreen(
            onBackButtonClicked = {}
        )
    }
}