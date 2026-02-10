package com.example.warrantywala.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageStorage {

    fun saveImageToInternalStorage(
        context: Context,
        uri: Uri
    ): String {

        val resolver = context.contentResolver

        val inputStream = resolver.openInputStream(uri)
            ?: throw RuntimeException("Cannot open input stream")

        val imagesDir = File(context.filesDir, "images")

        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }

        val file = File(
            imagesDir,
            "bill_${UUID.randomUUID()}.jpg"
        )

        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }

    fun deleteImage(path: String?) {

        if (path.isNullOrEmpty()) return

        try {

            val file = File(path)

            if (file.exists()) {
                file.delete()
            }

        } catch (_: Exception) {
        }
    }
}
