package com.example.warrantywala.ui.backup

import android.content.Context
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object BackupManager {

        fun backup(context: Context): Boolean {

            return try {

                val dbFile = context.getDatabasePath("warrantywala_db")

                val imagesDir = File(context.filesDir, "images")

                val backupDir = File(
                    context.getExternalFilesDir(null),
                    "WarrantyWala"
                )

                if (!backupDir.exists())
                    backupDir.mkdirs()

                val backupFile = File(backupDir, "backup.zip")

                ZipOutputStream(backupFile.outputStream()).use { zip ->

                    if (dbFile.exists()) {

                        zip.putNextEntry(
                            ZipEntry("warrantywala_db")
                        )

                        dbFile.inputStream().copyTo(zip)

                        zip.closeEntry()
                    }

                    if (imagesDir.exists()) {

                        imagesDir.listFiles()?.forEach { image ->

                            zip.putNextEntry(
                                ZipEntry("images/${image.name}")
                            )

                            image.inputStream().copyTo(zip)

                            zip.closeEntry()
                        }
                    }
                }

                true

            } catch (e: Exception) {

                e.printStackTrace()

                false
            }
        }
}
