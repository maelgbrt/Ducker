package com.example.ducker.ui.feature.frigo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ducker.Destination
import com.example.ducker.ui.UserFrigoViewModel

@Composable
fun UserFrigo(
    userId: String, // Reçu depuis ta navigation
    viewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit
) {
    val frigo by viewModel.frigo.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Frigo de l'utilisateur : $userId",
            style = MaterialTheme.typography.titleLarge
        )

        // Affichage des informations du frigo (basique avec toString ou personnalisé)

        Image(
            painter = rememberAsyncImagePainter(model = frigo?.photo),
            contentDescription = "Photo de profil",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            text = "J'essaye ça : ${frigo?.id ?: "Chargement ou pas de frigo trouvé..."}"
        )
    }
}