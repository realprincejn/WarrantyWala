package com.example.warrantywala.ui.add


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.warrantywala.data.local.AppDatabase
import com.example.warrantywala.data.repository.ApplianceRepository

class AddApplianceViewModelFactory(
    private val context: Context,
    private val applianceId: Int? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dao = AppDatabase.getDatabase(context).applianceDao()

        val repository = ApplianceRepository(dao)

        @Suppress("UNCHECKED_CAST")
        return AddApplianceViewModel(
            context,
            repository,
            applianceId
        ) as T

    }
    }

