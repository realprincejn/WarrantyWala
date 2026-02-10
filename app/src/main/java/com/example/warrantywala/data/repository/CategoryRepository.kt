package com.example.warrantywala.data.repository

import com.example.warrantywala.data.local.dao.CategoryDao
import com.example.warrantywala.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val dao: CategoryDao
) {

    fun getAllCategories(): Flow<List<CategoryEntity>> =
        dao.getAllCategories()

    suspend fun insertCategory(name: String) =
        dao.insertCategory(CategoryEntity(name = name))

    suspend fun updateCategory(category: CategoryEntity) =
        dao.updateCategory(category)

    suspend fun deleteCategory(category: CategoryEntity) =
        dao.deleteCategory(category)
}
