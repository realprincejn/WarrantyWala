package com.example.warrantywala.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.warrantywala.data.local.entity.ApplianceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplianceDao {
    @Query("""
    SELECT * FROM appliances
    WHERE name LIKE '%' || :query || '%'
    OR category LIKE '%' || :query || '%'
    ORDER BY warrantyExpiryDate ASC
""")
    fun searchAppliances(query: String): Flow<List<ApplianceEntity>>

    @Update
    suspend fun updateAppliance(appliance: ApplianceEntity)


    @Query("SELECT * FROM appliances ORDER BY warrantyExpiryDate ASC")
    fun getAllAppliances(): Flow<List<ApplianceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppliance(appliance: ApplianceEntity): Long

    @Query("DELETE FROM appliances WHERE id = :id")
    suspend fun deleteAppliance(id: Int)

    @Query("SELECT * FROM appliances WHERE id = :id LIMIT 1")
    suspend fun getApplianceById(id: Int): ApplianceEntity?



}
