package com.example.warrantywala.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.warrantywala.data.local.dao.ApplianceDao
import com.example.warrantywala.data.local.dao.CategoryDao
import com.example.warrantywala.data.local.entity.ApplianceEntity
import com.example.warrantywala.data.local.entity.CategoryEntity

@Database(
    entities = [ApplianceEntity::class,
        CategoryEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun applianceDao(): ApplianceDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "warrantywala_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
