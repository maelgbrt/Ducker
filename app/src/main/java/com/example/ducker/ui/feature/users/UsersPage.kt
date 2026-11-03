package com.example.ducker.ui.feature.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ducker.Destination
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.UserFrigoViewModel
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.ui.feature.fridge.IngredientItem

@Composable
fun UsersPage(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: DuckerViewModel,
    onNavigate: (Destination) -> Unit,
    maxImageSize: Dp = 300.dp,
    minImageSize: Dp = 100.dp
) {
    val users by viewModel.users.collectAsState()
    val matchedUser = users.find { it.id == userId }

    // Récupération du ViewModel du frigo pour cet utilisateur spécifique
    val userFrigoViewModel: UserFrigoViewModel = viewModel(
        key = "frigo_$userId",
        factory = UserFrigoViewModel.Factory(
            repository = viewModel.repository,
            userId = userId
        )
    )
    val ingredients by userFrigoViewModel.ingredients.collectAsState()

    val density = LocalDensity.current
    val maxPx = with(density) { maxImageSize.toPx() }
    val minPx = with(density) { minImageSize.toPx() }

    var scrollOffset by remember { mutableFloatStateOf(0f) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = matchedUser?.let { "${it.prenom}" } ?: "Profil",
                onBack = { onNavigate(Destination.FridgePage) },
                onSettings = { onNavigate(Destination.SettingsPage) }
            )
        },
        bottomBar = { BottomNavBar(isDarkMode = viewModel.isDarkMode, onNavigate = onNavigate) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (matchedUser == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Utilisateur introuvable")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(maxImageSize)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = matchedUser.photo),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "${matchedUser.prenom} ${matchedUser.nom}",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = matchedUser.localisation,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Frigo & Ingrédients",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                    // Affichage direct des ingrédients du frigo de l'utilisateur
                    items(ingredients) { ingredient ->
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
}