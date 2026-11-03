//package com.example.ducker.ui.feature.components

package com.example.ducker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ducker.Destination
import com.example.ducker.ui.theme.primaryLight

//[onSettings] = null masque le bouton paramètres.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuckerTopBar(
    title: String,
    onBack: () -> Unit,
    onSettings: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            TextButton(onClick = onBack) {
                Text(text = "Retour")
            }
        },
        actions = {
            if (onSettings != null) {
                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Paramètres"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun BottomNavBar(
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(primaryLight)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Button(onClick = { onNavigate(Destination.FridgePage) }) {
                Text(text = "My Fridge")
            }
            Button(onClick = { onNavigate(Destination.MatchsPage) }) {
                Text(text = "Explore")
            }
            /*Button(onClick = { onNavigate(Destination.UsersPage) }) {
                Text(text = "Matchs")
            }*/
        }
    }
}