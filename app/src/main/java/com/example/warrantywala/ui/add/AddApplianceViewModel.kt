package com.example.warrantywala.ui.add

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warrantywala.data.local.ImageStorage
import com.example.warrantywala.data.local.entity.ApplianceEntity
import com.example.warrantywala.data.notification.WarrantyScheduler
import com.example.warrantywala.data.repository.ApplianceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class AddApplianceViewModel(
    private val context: Context,
    private val repository: ApplianceRepository,
    private val applianceId: Int? = null
) : ViewModel() {

    private val _state = MutableStateFlow(AddApplianceState())
    val state: StateFlow<AddApplianceState> = _state.asStateFlow()

    init {
        applianceId?.let {
            loadAppliance(it)
        }
    }

    private fun loadAppliance(id: Int) {

        viewModelScope.launch {

            val appliance = repository.getApplianceById(id)

            appliance?.let {

                _state.value = AddApplianceState(
                    name = it.name,
                    category = it.category,
                    warrantyMonths = it.warrantyMonths,
                    purchaseDate = it.purchaseDate,
                    billImageUri = it.billImageUri
                )
            }
        }
    }

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun onCategoryChange(value: String) {
        _state.value = _state.value.copy(category = value)
    }

    fun onWarrantyChange(months: Int) {
        _state.value = _state.value.copy(warrantyMonths = months)
    }

    fun onPurchaseDateChange(date: Long) {
        _state.value = _state.value.copy(purchaseDate = date)
    }

    fun onBillImageSelected(path: String) {
        _state.value = _state.value.copy(billImageUri = path)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAppliance(context: Context) {

        viewModelScope.launch {

            try {

                val expiryDate =
                    Instant.ofEpochMilli(_state.value.purchaseDate)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .plusMonths(_state.value.warrantyMonths.toLong())
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()


                val appliance = ApplianceEntity(
                    id = applianceId ?: 0,
                    name = _state.value.name,
                    category = _state.value.category,
                    purchaseDate = _state.value.purchaseDate,
                    warrantyMonths = _state.value.warrantyMonths,
                    warrantyExpiryDate = expiryDate,
                    notes = null,
                    billImageUri = _state.value.billImageUri
                )


                // ✅ THIS IS THE FIX
                val finalId: Int

                if (applianceId == null) {

                    // INSERT NEW and get generated ID
                    finalId = repository.insertAppliance(appliance).toInt()

                } else {

                    val old = repository.getApplianceById(applianceId)

                    if (
                        old?.billImageUri != null &&
                        old.billImageUri != appliance.billImageUri
                    ) {
                        ImageStorage.deleteImage(old.billImageUri)
                    }

                    repository.updateAppliance(appliance)

                    finalId = applianceId
                }


                // ✅ NOW notification works correctly
                WarrantyScheduler.schedule(
                    context = context,
                    applianceId = finalId,
                    applianceName = appliance.name,
                    expiryDate = expiryDate
                )

            } catch (e: Exception) {

                e.printStackTrace()

            }
        }
    }

}
