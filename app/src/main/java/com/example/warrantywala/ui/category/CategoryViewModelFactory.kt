package com.example.warrantywala.ui.category

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.warrantywala.data.local.AppDatabase
import com.example.warrantywala.data.repository.CategoryRepository

class CategoryViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dao =
            AppDatabase.getDatabase(context).categoryDao()

        val repository =
            CategoryRepository(dao)

        return CategoryViewModel(repository) as T
    }
}
