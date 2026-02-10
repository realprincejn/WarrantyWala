package com.example.warrantywala.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warrantywala.data.repository.ApplianceRepository
import com.example.warrantywala.data.local.entity.ApplianceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine


class DashboardViewModel(
    private val repository: ApplianceRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")



    init {
        observeAppliances()
    }

    fun deleteAppliance(id: Int) {

        viewModelScope.launch {

            repository.deleteAppliance(id)

        }
    }

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    private fun observeAppliances() {

        viewModelScope.launch {

            combine(
                repository.getAllAppliances(),
                _searchQuery,
                _selectedCategory
            ) { appliances, query, category ->

                var filtered = appliances

                if (category != "All") {
                    filtered = filtered.filter {
                        it.category.equals(category, ignoreCase = true)
                    }
                }

                if (query.isNotBlank()) {
                    filtered = filtered.filter {
                        it.name.contains(query, true) ||
                                it.category.contains(query, true)
                    }
                }

                val categories =
                    listOf("All") + appliances.map { it.category }.distinct()

                DashboardState(
                    appliances = filtered.map { it.toUIModel() },
                    categories = categories,
                    selectedCategory = category
                )

            }.collect {
                _state.value = it
            }
        }
    }



}
