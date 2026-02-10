package com.example.warrantywala.ui

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Category
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.warrantywala.navigation.Screen
import com.example.warrantywala.ui.dashboard.DashboardScreen
import com.example.warrantywala.ui.add.AddApplianceScreen
import com.example.warrantywala.ui.category.CategoryScreen
import com.example.warrantywala.ui.detail.ApplianceDetailScreen
import com.example.warrantywala.ui.imageviewer.ImageViewerScreen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(

        bottomBar = {

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Dashboard
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Dashboard.route)
                                launchSingleTop = true
                            }
                        }
                    ) {

                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text("Dashboard")
                    }

                    // Add
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.AddAppliance.route)
                        },
                        shape = CircleShape
                    ) {

                        Icon(Icons.Default.Add, null)

                    }

                    // Categories
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Categories.route) {
                                launchSingleTop = true
                            }
                        }
                    ) {

                        Icon(
                            Icons.Default.Category,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text("Categories")
                    }

                }
            }
        }

    ) { padding ->

        NavHost(

            navController = navController,

            startDestination = Screen.Dashboard.route,

            modifier = Modifier.padding(padding)

        ) {

            // REMOVE IMAGE VIEWER ROUTE COMPLETELY

            composable(Screen.Dashboard.route) {

                DashboardScreen(

                    onItemClick = { id ->

                        navController.navigate(
                            Screen.Detail.createRoute(id)
                        )

                    },

                    onAddClick = {

                        navController.navigate(
                            Screen.AddAppliance.route
                        )

                    },

                    onImageClick = {
                        // DO NOTHING
                        // handled inside DashboardScreen overlay
                    }

                )

            }

            composable(Screen.Categories.route) {

                CategoryScreen()

            }

            composable(Screen.AddAppliance.route) {

                AddApplianceScreen(
                    onSaved = {
                        navController.popBackStack()
                    }
                )

            }

            composable(

                route = Screen.EditAppliance.route,

                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )

            ) {

                val id = it.arguments?.getInt("id")

                AddApplianceScreen(
                    applianceId = id,
                    onSaved = {
                        navController.popBackStack()
                    }
                )

            }

            composable(

                route = Screen.Detail.route,

                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )

            ) {

                val id =
                    it.arguments?.getInt("id")
                        ?: return@composable

                ApplianceDetailScreen(

                    applianceId = id,

                    onBack = {
                        navController.popBackStack()
                    },

                    onEdit = {

                        navController.navigate(
                            Screen.EditAppliance.createRoute(it)
                        )

                    }

                )

            }

        }

    }

}
