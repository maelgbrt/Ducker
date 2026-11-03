package com.example.ducker.ui.feature.frigo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ducker.Destination
import com.example.ducker.ui.UserFrigoViewModel
import com.example.ducker.ui.components.DuckerTopBar

@Composable
fun UserFrigo(
    userId: String, // Reçu depuis ta navigation
    viewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit
) {
    val frigo by viewModel.frigo.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = "Frigo",
                onBack = { onNavigate(Destination.HomePage) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Frigo de l'utilisateur : $userId",
                style = MaterialTheme.typography.titleLarge
            )

            val currentFrigo = frigo
            if (currentFrigo == null) {
                Text(text = "Chargement ou aucun frigo trouvé...")
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = currentFrigo.photo), //erreur : Unresolved reference 'rememberAsyncImagePainter'.
                    contentDescription = "Photo du frigo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Text(
                    text = "Frigo n° ${currentFrigo.id}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
