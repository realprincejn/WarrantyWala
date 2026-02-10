package com.example.warrantywala.ui.detail

import com.example.warrantywala.ui.dashboard.WarrantyStatus

data class ApplianceDetailState(
    val name: String = "",
    val category: String = "",
    val warrantyStatus: WarrantyStatus = WarrantyStatus.ACTIVE,
    val daysLeft: Int = 0,
    val billImageUri: String? = null
)
