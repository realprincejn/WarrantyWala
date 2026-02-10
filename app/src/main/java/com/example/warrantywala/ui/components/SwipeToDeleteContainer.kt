package com.example.warrantywala.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(

    onDelete: () -> Unit,

    content: @Composable () -> Unit

) {

    val dismissState = rememberDismissState(

        confirmValueChange = { dismissValue ->

            if (dismissValue == DismissValue.DismissedToStart) {

                onDelete()

            }

            false // prevents auto removal
        }

    )

    SwipeToDismiss(

        state = dismissState,

        directions = setOf(DismissDirection.EndToStart),

        background = {

            DeleteBackground(dismissState)

        },

        dismissContent = {

            content()

        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(

    dismissState: DismissState

) {

    val color by animateColorAsState(

        when (dismissState.targetValue) {

            DismissValue.Default -> Color.Transparent

            else -> Color(0xFFE53935) // red

        },

        label = ""
    )

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),

        contentAlignment = Alignment.CenterEnd

    ) {

        Icon(

            imageVector = Icons.Default.Delete,

            contentDescription = "Delete",

            tint = Color.White

        )

    }

}
