package com.example.warrantywala.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.warrantywala.R

class WarrantyReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

        Log.d("WarrantyReceiver", "Receiver triggered!")
        val applianceName =
            intent.getStringExtra("applianceName") ?: "Your appliance"

        val expiryDate =
            intent.getLongExtra("expiryDate", 0L)

        val now = System.currentTimeMillis()

        val daysLeft =
            ((expiryDate - now) / (1000 * 60 * 60 * 24)).toInt()

        val (title, message) = when {

            daysLeft < 0 -> Pair(
                "💀 Warranty expired!",
                "$applianceName warranty is gone. You raw-dogging repairs now."
            )

            daysLeft == 0 -> Pair(
                "⚠️ LAST DAY!",
                "$applianceName warranty expires today. This is your final boss fight."
            )

            daysLeft <= 3 -> Pair(
                "🚨 Critical warning",
                "$applianceName expires in $daysLeft days. Act fast or regret later."
            )

            daysLeft <= 7 -> Pair(
                "⏳ Warranty ending soon",
                "$applianceName expires in $daysLeft days. Don't sleep on it."
            )

            else -> Pair(
                "📢 Warranty reminder",
                "$applianceName has $daysLeft days left. You're chilling... for now."
            )
        }

        NotificationHelper.createChannel(context)

        NotificationHelper.showNotification(
            context,
            title,
            message
        )
    }

}
