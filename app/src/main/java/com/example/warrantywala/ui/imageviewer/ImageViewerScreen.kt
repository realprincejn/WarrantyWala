package com.example.warrantywala.ui.imageviewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.io.File

@Composable
fun ImageViewerScreen(uri: String) {

    if (uri.isNotEmpty()) {

        AsyncImage(
            model = File(uri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentScale = ContentScale.Fit
        )
    }
}


