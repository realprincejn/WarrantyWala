package com.example.warrantywala.ui.dashboard

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warrantywala.ui.components.ApplianceCard
import com.example.warrantywala.ui.components.CategoryChips
import com.example.warrantywala.ui.components.SearchBar

@Composable
fun DashboardScreen(
    context: Context = LocalContext.current,
    onItemClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onImageClick : (String)-> Unit
) {

    val viewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(context)
    )
    val state by viewModel.state.collectAsState()
    val query by viewModel.searchQuery.collectAsState()


    Scaffold(

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {


            Text(
                text = "Track all your warranties",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            CategoryChips(

                categories = state.categories,

                selected = state.selectedCategory,

                onSelected = viewModel::onCategorySelected

            )

            Spacer(modifier = Modifier.height(12.dp))

            SearchBar(
                query = query,
                onQueryChange = viewModel::onSearchChange
            )


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(state.appliances) { appliance ->
                    ApplianceCard(

                        appliance = appliance,

                        onClick = {
                            onItemClick(appliance.id)
                        },

                        onImageClick = onImageClick
                    )

                }
            }

        }
        }

}
