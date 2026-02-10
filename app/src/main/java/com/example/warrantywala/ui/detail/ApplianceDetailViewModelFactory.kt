package com.example.warrantywala.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.warrantywala.data.local.AppDatabase
import com.example.warrantywala.data.repository.ApplianceRepository

class ApplianceDetailViewModelFactory(
    private val context: Context,
    private val applianceId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplianceDetailViewModel::class.java)) {

            val dao = AppDatabase
                .getDatabase(context)
                .applianceDao()

            val repository = ApplianceRepository(dao)

            @Suppress("UNCHECKED_CAST")
            return ApplianceDetailViewModel(repository, applianceId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
