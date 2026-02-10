package com.example.warrantywala.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.warrantywala.data.local.AppDatabase
import com.example.warrantywala.data.repository.ApplianceRepository

class DashboardViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {

            val dao = AppDatabase
                .getDatabase(context)
                .applianceDao()

            val repository = ApplianceRepository(dao)

            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
