package com.example.ducker.ui.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ducker.Destination
import com.example.ducker.R
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar

val PrimaryPurple = Color(0xFF7B2CBF)
val SecondaryPurple = Color(0xFF9D4EDD)
val BackgroundLight = Color(0xFFA5A5A5)

val CardBackground = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1C1B1F)
val TextSecondary = Color(0xFF625B71)

@Composable
fun SettingsItem(name: String, desc: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.leading_element), // Placeholder
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsPage(
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = "Paramètres",
                onBack = { onNavigate(Destination.HomePage) }
            )
        },
        bottomBar = { BottomNavBar(onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // TODO : brancher chaque action quand les pages existeront
            SettingsItem(
                name = "Mode sombre",
                desc = "Bientôt disponible",
                onClick = { }
            )
            SettingsItem(
                name = "Mon compte",
                desc = "Bientôt disponible",
                onClick = { }
            )
            SettingsItem(
                name = "Changer de frigo",
                desc = "Voir mon frigo",
                onClick = { onNavigate(Destination.FridgePage) }
            )
            SettingsItem(
                name = "Mes repas",
                desc = "Bientôt disponible",
                onClick = { }
            )
        }
    }
}

