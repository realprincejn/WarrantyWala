package com.example.warrantywala.ui.add

import com.example.warrantywala.ui.category.CategoryViewModel
import com.example.warrantywala.ui.category.CategoryViewModelFactory
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.layout.ContentScale
import android.net.Uri
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import coil.compose.AsyncImage
import com.example.warrantywala.data.local.ImageStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApplianceScreen(
    applianceId: Int? = null,
    onSaved: () -> Unit
) {
    val context = LocalContext.current

    val viewModel: AddApplianceViewModel = viewModel(
        factory = AddApplianceViewModelFactory(
            context,
            applianceId
        )
    )
    val categoryViewModel: CategoryViewModel = viewModel(
        factory = CategoryViewModelFactory(context)
    )


    val state by viewModel.state.collectAsState()

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            uri?.let {

                try {

                    val path =
                        ImageStorage.saveImageToInternalStorage(
                            context,
                            it
                        )

                    viewModel.onBillImageSelected(path)

                } catch (e: Exception) {

                    e.printStackTrace()

                }

            }
        }

    state.billImageUri?.let { path ->

        AsyncImage(
            model = File(path),
            contentDescription = "Bill Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "Add Appliance",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Appliance name") },
            modifier = Modifier.fillMaxWidth()
        )

        val categories by categoryViewModel.categories.collectAsState()

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .heightIn(max = 100.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),

                value = state.category,

                onValueChange = {},

                readOnly = true,

                label = { Text("Category") },

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                categories.forEach {

                    DropdownMenuItem(
                        text = { Text(it.name) },

                        onClick = {

                            viewModel.onCategoryChange(it.name)

                            expanded = false

                        }
                    )
                }
            }
        }



        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val dateText = formatter.format(Date(state.purchaseDate))

        OutlinedTextField(

            value = dateText,

            onValueChange = {},

            label = { Text("Purchase Date") },

            modifier = Modifier.fillMaxWidth(),

            readOnly = true,

            trailingIcon = {

                IconButton(onClick = {

                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = state.purchaseDate

                    DatePickerDialog(
                        context,
                        { _, year, month, day ->

                            val newCalendar = Calendar.getInstance()
                            newCalendar.set(year, month, day)

                            viewModel.onPurchaseDateChange(
                                newCalendar.timeInMillis
                            )

                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()

                }) {

                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            }
        )
        OutlinedTextField(
            value = state.warrantyMonths.toString(),

            onValueChange = { value ->
                value.toIntOrNull()?.let { months ->
                    viewModel.onWarrantyChange(months)
                }
            },
            label = { Text("Warranty (months)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { imagePickerLauncher.launch(arrayOf("image/*")) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Upload Bill Image")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveAppliance(context)
                onSaved()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Save")
        }
    }
}
