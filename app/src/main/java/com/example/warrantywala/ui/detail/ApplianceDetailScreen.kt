package com.example.warrantywala.ui.detail

import com.example.warrantywala.ui.dashboard.WarrantyStatus

import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun ApplianceDetailScreen(
    applianceId: Int,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val context = LocalContext.current

    val viewModel: ApplianceDetailViewModel = viewModel(
        factory = ApplianceDetailViewModelFactory(context, applianceId)
    )

    val state by viewModel.state.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    state.billImageUri?.let { uriString ->
        val bitmap = remember(uriString) {

            try {

                MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(uriString)
                )

            } catch (e: Exception) {

                null

            }
        }


        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Bill Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Appliance") },
            text = { Text("Are you sure you want to delete this appliance? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteAppliance {
                            onBack()
                        }
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = state.name,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Category: ${state.category}"
        )

        Text(
            text = when (state.warrantyStatus) {
                WarrantyStatus.ACTIVE ->
                    "Warranty Active • ${state.daysLeft} days left"
                WarrantyStatus.EXPIRING ->
                    "Warranty Expiring • ${state.daysLeft} days left"
                WarrantyStatus.EXPIRED ->
                    "Warranty Expired"
            }
        )



        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
        Button(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Delete Appliance")
        }
        Button(
            onClick = {
                onEdit(applianceId)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Appliance")
        }


    }
}
