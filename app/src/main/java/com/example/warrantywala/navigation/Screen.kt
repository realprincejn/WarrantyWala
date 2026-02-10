package com.example.warrantywala.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddAppliance : Screen("add_appliance")

    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int): String {
            return "detail/$id"
        }
    }
    object Categories : Screen("categories")

    object EditAppliance : Screen("edit/{id}") {

        fun createRoute(id: Int): String {
            return "edit/$id"
        }
    }

    object ImageViewer : Screen("imageViewer/{uri}") {

        fun createRoute(uri: String): String {

            val encoded = Uri.encode(uri)

            return "imageViewer/$encoded"
        }
    }



}
