package com.example.warrantywala.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips(

    categories: List<String>,

    selected: String,

    onSelected: (String) -> Unit

) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(categories) { category ->

            FilterChip(

                selected = category == selected,

                onClick = {
                    onSelected(category)
                },

                label = {
                    Text(category)
                }
            )
        }
    }
}
