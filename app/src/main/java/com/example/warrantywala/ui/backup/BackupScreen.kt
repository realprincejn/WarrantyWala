package com.example.warrantywala.ui.backup

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BackupScreen() {

    var autoBackup by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Folder picker launcher (for Google Drive or any storage)
    val folderPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->

        if (uri != null) {

            DriveBackupManager.saveFolderUri(context, uri)

            val success = DriveBackupManager.backup(context)

            Toast.makeText(
                context,
                if (success)
                    "Backup saved successfully"
                else
                    "Backup failed",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {

        Text(
            text = "Backup & Restore",
            style = MaterialTheme.typography.headlineSmall
        )

        // BACKUP BUTTON
        Button(

            onClick = {

                val existingFolder =
                    DriveBackupManager.getFolderUri(context)

                if (existingFolder == null) {

                    // Ask user to pick folder first time
                    folderPicker.launch(null)

                } else {

                    val success =
                        DriveBackupManager.backup(context)

                    Toast.makeText(
                        context,
                        if (success)
                            "Backup saved successfully"
                        else
                            "Backup failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text("Backup to Google Drive")

        }


        // RESTORE BUTTON (we'll implement next)
        Button(

            onClick = {

                Toast.makeText(
                    context,
                    "Restore coming soon",
                    Toast.LENGTH_SHORT
                ).show()

            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text("Restore Backup")

        }


        // AUTO BACKUP SWITCH
        Row(

            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(
                text = "Auto Backup",
                modifier = Modifier.weight(1f)
            )

            Switch(

                checked = autoBackup,

                onCheckedChange = {

                    autoBackup = it

                    Toast.makeText(
                        context,
                        if (it)
                            "Auto backup enabled"
                        else
                            "Auto backup disabled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            )

        }

    }
}
