package com.example.warrantywala.ui.dashboard

import com.example.warrantywala.data.local.entity.ApplianceEntity
import java.util.concurrent.TimeUnit

fun ApplianceEntity.toUIModel(): DashboardApplianceUI {

    val now = System.currentTimeMillis()
    val daysLeft = TimeUnit.MILLISECONDS.toDays(warrantyExpiryDate - now).toInt()

    val status = when {
        daysLeft < 0 -> WarrantyStatus.EXPIRED
        daysLeft <= 30 -> WarrantyStatus.EXPIRING
        else -> WarrantyStatus.ACTIVE
    }

    return DashboardApplianceUI(
        id = id,
        name = name,
        warrantyStatus = status,
        daysLeft = maxOf(daysLeft, 0),
        billImageUri = billImageUri
    )
}
