package com.example.warrantywala.data.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WarrantyWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val applianceName =
            inputData.getString("name") ?: return Result.failure()

        NotificationHelper.showNotification(
            applicationContext,
            "Warranty Expiring Soon",
            "$applianceName warranty is about to expire"
        )

        return Result.success()
    }
}
