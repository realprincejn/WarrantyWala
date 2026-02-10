package com.example.warrantywala.ui.category


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warrantywala.data.local.AppDatabase
import com.example.warrantywala.data.repository.CategoryRepository

@Composable
fun CategoryScreen() {

    val context = LocalContext.current

    val viewModel: CategoryViewModel = viewModel(
        factory = CategoryViewModelFactory(context)
    )

    val categories by viewModel.categories.collectAsState()

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Categories", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {

                    if (text.isNotBlank()) {

                        viewModel.addCategory(text)

                        text = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(categories) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    Text(it.name)

                    TextButton(
                        onClick = {
                            viewModel.deleteCategory(it)
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
