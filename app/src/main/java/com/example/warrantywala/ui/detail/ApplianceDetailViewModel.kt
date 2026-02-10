package com.example.warrantywala.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warrantywala.data.local.entity.ApplianceEntity
import com.example.warrantywala.data.repository.ApplianceRepository
import com.example.warrantywala.ui.dashboard.WarrantyStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplianceDetailViewModel(
    private val repository: ApplianceRepository,
    private val applianceId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(ApplianceDetailState())
    val state: StateFlow<ApplianceDetailState> = _state.asStateFlow()

    init {
        loadAppliance()
    }

    private fun loadAppliance() {
        viewModelScope.launch {
            repository.getAllAppliances().collect { list ->
                val appliance = list.firstOrNull { it.id == applianceId }
                appliance?.let { mapToState(it) }
            }
        }
    }

    private fun mapToState(entity: ApplianceEntity) {
        val now = System.currentTimeMillis()
        val daysLeft =
            TimeUnit.MILLISECONDS.toDays(entity.warrantyExpiryDate - now).toInt()

        val status = when {
            daysLeft < 0 -> WarrantyStatus.EXPIRED
            daysLeft <= 30 -> WarrantyStatus.EXPIRING
            else -> WarrantyStatus.ACTIVE
        }

        _state.value = ApplianceDetailState(
            name = entity.name,
            category = entity.category,
            warrantyStatus = status,
            daysLeft = maxOf(daysLeft, 0),
            billImageUri = entity.billImageUri
        )
    }
    fun deleteAppliance(onDeleted: () -> Unit) {
        viewModelScope.launch {
            repository.deleteAppliance(applianceId)
            onDeleted()
        }
    }

}
