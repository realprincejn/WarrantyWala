
package com.example.warrantywala.ui.add

data class AddApplianceState(

    val name: String = "",

    val category: String = "",

    val warrantyMonths: Int = 12,

    val purchaseDate: Long = System.currentTimeMillis(),

    val notes: String = "",

    val billImageUri: String? = null

)
