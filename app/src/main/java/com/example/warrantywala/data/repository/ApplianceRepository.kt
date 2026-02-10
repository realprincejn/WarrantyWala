package com.example.warrantywala.data.repository


import com.example.warrantywala.data.local.ImageStorage
import com.example.warrantywala.data.local.dao.ApplianceDao
import com.example.warrantywala.data.local.entity.ApplianceEntity
import kotlinx.coroutines.flow.Flow

class ApplianceRepository(
    private val applianceDao: ApplianceDao
) {

    suspend fun updateAppliance(appliance: ApplianceEntity) {
        applianceDao.updateAppliance(appliance)
    }

    fun searchAppliances(query: String): Flow<List<ApplianceEntity>> {
        return applianceDao.searchAppliances(query)
    }
    fun getAllAppliances(): Flow<List<ApplianceEntity>> {
        return applianceDao.getAllAppliances()
    }
    suspend fun insertAppliance(appliance: ApplianceEntity): Long {
        return applianceDao.insertAppliance(appliance)
    }
    suspend fun getApplianceById(id: Int) =
        applianceDao.getApplianceById(id)



    suspend fun deleteAppliance(id: Int) {

        val appliance =
            applianceDao.getApplianceById(id)

        ImageStorage.deleteImage(
            appliance?.billImageUri
        )

        applianceDao.deleteAppliance(id)
    }

}
