package com.example.ducker.ui.feature.frigo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ducker.Destination
import com.example.ducker.local.entity.UserEntity
import com.example.ducker.ui.UserFrigoViewModel
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.ui.feature.fridge.IngredientItem
import com.example.ducker.ui.theme.onPrimaryLight
import com.example.ducker.ui.theme.primaryLight

@Composable
fun UserFrigo(
    userId: String,
    viewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit,
    owner: UserEntity? = null
) {
    val frigo by viewModel.frigo.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val currentFrigo = frigo

    // Photo de l'en-tête : celle du frigo si elle existe, sinon celle du propriétaire
    val frigoPhoto = currentFrigo?.photo
    val heroPhoto = if (!frigoPhoto.isNullOrBlank()) frigoPhoto else owner?.photo

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = owner?.let { "Frigo de ${it.prenom}" } ?: "Frigo",
                onBack = { onNavigate(Destination.HomePage) },
                onSettings = { onNavigate(Destination.SettingsPage) }
            )
        },
        bottomBar = { BottomNavBar(onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // ---- En-tête : photo pleine largeur avec le nom par-dessus ----
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    if (heroPhoto.isNullOrBlank()) {
                        // Pas de photo : un fond de couleur avec un emoji
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🧊", fontSize = 64.sp)
                        }
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(model = heroPhoto),
                            contentDescription = "Photo du frigo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = owner?.let { "${it.prenom} ${it.nom}" } ?: "Utilisateur $userId",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (owner != null) {
                            Text(
                                text = owner.localisation,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // ---- Carte d'infos, même style que la page Mon frigo ----
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(primaryLight)
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (currentFrigo != null) {
                            "Frigo n° ${currentFrigo.id}"
                        } else {
                            "Chargement du frigo..."
                        },
                        color = onPrimaryLight,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (currentFrigo != null) {
                        Text(
                            text = "${ingredients.size} ingrédients",
                            color = onPrimaryLight,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // ---- Contenu du frigo ----
            item {
                Text(
                    text = "Contenu du frigo",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            if (ingredients.isEmpty()) {
                item {
                    Text(
                        text = "Aucun ingrédient dans ce frigo pour le moment.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                items(items = ingredients) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}