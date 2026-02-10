package com.example.warrantywala.ui.dashboard

data class DashboardState(
    val appliances: List<DashboardApplianceUI> = emptyList(),
    val categories: List<String> = emptyList(),

    val selectedCategory: String = "All"
)
