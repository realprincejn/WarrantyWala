package com.example.warrantywala.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.warrantywala.ui.dashboard.DashboardApplianceUI
import com.example.warrantywala.ui.dashboard.WarrantyStatus
import java.io.File

@Composable
fun ApplianceCard(
    appliance: DashboardApplianceUI,
    onClick: () -> Unit,
    onImageClick: (String) -> Unit

) {

    val badgeColor = when (appliance.warrantyStatus) {
        WarrantyStatus.ACTIVE -> Color(0xFF2E7D32)
        WarrantyStatus.EXPIRING -> Color(0xFFF57C00)
        WarrantyStatus.EXPIRED -> Color(0xFFC62828)
    }

    val badgeText = when (appliance.warrantyStatus) {
        WarrantyStatus.ACTIVE -> "${appliance.daysLeft} days left"
        WarrantyStatus.EXPIRING -> "Expiring • ${appliance.daysLeft} days left"
        WarrantyStatus.EXPIRED -> "Expired"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //image icon
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .clickable {

                        appliance.billImageUri?.let {

                            onImageClick(it)

                        }
                    }
            ) {


                    if (!appliance.billImageUri.isNullOrEmpty()) {

                        AsyncImage(
                            model = File(appliance.billImageUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                        )
                    }

            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = appliance.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = badgeText,
                    color = badgeColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
