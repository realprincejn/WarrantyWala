package com.example.warrantywala.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warrantywala.data.local.defaultCategories
import com.example.warrantywala.data.local.entity.CategoryEntity
import com.example.warrantywala.data.repository.CategoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    val categories =
        repository.getAllCategories()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    init {
        insertDefaultsIfNeeded()
    }

    private fun insertDefaultsIfNeeded() {

        viewModelScope.launch {

            repository.getAllCategories().first().let { existing ->

                if (existing.isEmpty()) {

                    defaultCategories.forEach {

                        repository.insertCategory(it.name)

                    }
                }
            }
        }
    }

    fun addCategory(name: String) {

        viewModelScope.launch {

            repository.insertCategory(name)

        }
    }

    fun deleteCategory(category: CategoryEntity) {

        viewModelScope.launch {

            repository.deleteCategory(category)

        }
    }
}
