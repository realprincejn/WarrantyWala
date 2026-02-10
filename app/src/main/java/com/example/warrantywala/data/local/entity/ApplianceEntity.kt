package com.example.warrantywala.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appliances")
data class ApplianceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val category: String,
    val purchaseDate: Long,
    val warrantyMonths: Int,
    val warrantyExpiryDate: Long,
    val notes: String?,
    val billImageUri: String?
)
