package com.example.warrantywala.ui.imageviewer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.io.File

@Composable
fun ImageViewerScreen(

    uri: String,

    visible: Boolean,

    onClose: () -> Unit

) {

    AnimatedVisibility(

        visible = visible,

        enter = fadeIn() + scaleIn(initialScale = 0.9f),

        exit = fadeOut() + scaleOut(targetScale = 0.9f)

    ) {

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable {
                    onClose()
                },

            contentAlignment = Alignment.Center

        ) {

            AsyncImage(

                model = File(uri),

                contentDescription = null,

                modifier = Modifier.fillMaxSize(),

                contentScale = ContentScale.Fit

            )

        }
    }
}
