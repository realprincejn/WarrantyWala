package com.example.warrantywala.ui.dashboard

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warrantywala.R
import com.example.warrantywala.ui.components.*
import com.example.warrantywala.ui.imageviewer.ImageViewerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(

    context: Context = LocalContext.current,

    onItemClick: (Int) -> Unit,

    onAddClick: () -> Unit,

    onImageClick: (String) -> Unit,// keep this (don't remove)
    onBackupClick: () -> Unit

) {

    val viewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(context)
    )

    val state by viewModel.state.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    // IMAGE VIEWER STATE
    var viewerVisible by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf("") }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var applianceToDelete by remember { mutableStateOf<Int?>(null) }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.mipmap.warrantywala_foreground),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {

                            Text(
                                "WarrantyWala",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                "Track all your warranties",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },

                actions = {

                    IconButton(

                        onClick = {
                            onBackupClick()
                        }

                    ) {

                        Icon(
                            imageVector = Icons.Default.SettingsBackupRestore,
                            contentDescription = "Backup"
                        )

                    }

                }
            )
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                CategoryChips(
                    categories = state.categories,
                    selected = state.selectedCategory,
                    onSelected = viewModel::onCategorySelected
                )

                Spacer(modifier = Modifier.height(14.dp))

                SearchBar(
                    query = query,
                    onQueryChange = viewModel::onSearchChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.appliances.isEmpty()) {

                    EmptyState()

                } else {

                    LazyColumn(

                        modifier = Modifier.fillMaxSize(),

                        verticalArrangement = Arrangement.spacedBy(16.dp),

                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )

                    ) {

                        items(
                            items = state.appliances,
                            key = { it.id }
                        ) { appliance ->

                            SwipeToDeleteContainer(

                                onDelete = {
                                    applianceToDelete = appliance.id
                                    showDeleteDialog = true
                                }

                            ) {

                                ApplianceCard(

                                    appliance = appliance,

                                    onClick = {
                                        onItemClick(appliance.id)
                                    },

                                    onImageClick = { uri ->

                                        selectedImage = uri
                                        viewerVisible = true

                                    }

                                )

                            }

                        }

                    }

                }

            }

            // IMAGE VIEWER OVERLAY
            ImageViewerScreen(

                uri = selectedImage,

                visible = viewerVisible,

                onClose = {

                    viewerVisible = false

                }

            )


            if (showDeleteDialog) {

                AlertDialog(

                    onDismissRequest = {
                        showDeleteDialog = false
                    },

                    title = {
                        Text("Delete Appliance?")
                    },

                    text = {
                        Text("This action cannot be undone.")
                    },

                    confirmButton = {

                        TextButton(

                            onClick = {

                                applianceToDelete?.let {

                                    viewModel.deleteAppliance(it)

                                }

                                showDeleteDialog = false
                            }

                        ) {

                            Text(
                                "Delete",
                                color = MaterialTheme.colorScheme.error
                            )

                        }

                    },

                    dismissButton = {

                        TextButton(

                            onClick = {

                                showDeleteDialog = false

                            }

                        ) {

                            Text("Cancel")

                        }

                    }

                )

            }


        }

    }

}
