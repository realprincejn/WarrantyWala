package com.example.warrantywala.ui.backup


import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object DriveBackupManager {

    private const val PREF = "backup_pref"
    private const val KEY_URI = "backup_folder_uri"

    fun saveFolderUri(context: Context, uri: Uri) {

        context.contentResolver.takePersistableUriPermission(
            uri,
            android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_URI, uri.toString())
            .apply()
    }

    fun getFolderUri(context: Context): Uri? {

        val uri = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getString(KEY_URI, null)

        return uri?.let { Uri.parse(it) }
    }

    fun backup(context: Context): Boolean {

        val folderUri = getFolderUri(context) ?: return false

        val folder = DocumentFile.fromTreeUri(context, folderUri)
            ?: return false

        val backupFile = folder.createFile(
            "application/zip",
            "warrantywala_backup"
        ) ?: return false

        val dbFile = context.getDatabasePath("warrantywala_db")

        val imagesDir = File(context.filesDir, "images")

        context.contentResolver.openOutputStream(backupFile.uri)?.use { out ->

            ZipOutputStream(out).use { zip ->

                if (dbFile.exists()) {

                    zip.putNextEntry(ZipEntry("warrantywala_db"))

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
        }

        return true
    }
}
