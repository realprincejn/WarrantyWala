package com.example.warrantywala.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

object WarrantyScheduler {

    fun schedule(
        context: Context,
        applianceId: Int,
        applianceName: String,
        expiryDate: Long
    ) {

        val intent = Intent(context, WarrantyReceiver::class.java).apply {
            putExtra("applianceId", applianceId)
            putExtra("applianceName", applianceName)
            putExtra("expiryDate", expiryDate)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            applianceId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val triggerTime = expiryDate

        Log.d("WarrantyScheduler", "Scheduling alarm for $applianceName at $triggerTime")

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                if (alarmManager.canScheduleExactAlarms()) {

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )

                } else {

                    // fallback if permission not granted
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }

            } else {

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )

            }

        } catch (e: Exception) {

            Log.e("WarrantyScheduler", "Alarm scheduling failed", e)

            // guaranteed fallback
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}
