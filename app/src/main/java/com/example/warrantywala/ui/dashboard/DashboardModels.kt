package com.example.warrantywala.ui.dashboard

data class DashboardApplianceUI(
    val id: Int,
    val name: String,
    val warrantyStatus: WarrantyStatus,
    val daysLeft: Int,
    val billImageUri: String?
)

enum class WarrantyStatus {
    ACTIVE,
    EXPIRING,
    EXPIRED
}


