package com.example.ducker.ui.feature.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import androidx.compose.foundation.lazy.items

@Composable
fun UsersPage(
    modifier: Modifier = Modifier,
    maxImageSize: Dp = 300.dp,
    minImageSize: Dp = 100.dp,
    viewModel: DuckerViewModel,
    onNavigate: (Destination) -> Unit,
    userId: String,
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
    var headerPx by remember { mutableFloatStateOf(maxPx) }
    val listState = rememberLazyListState()

    val nestedScrollConnection = remember(minPx, maxPx) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                // Doigt vers le haut : l'en-tête rétrécit d'abord.
                // Doigt vers le bas : il regrandit seulement quand la liste est revenue en haut.
                val shouldResize = delta < 0 || (delta > 0 && !listState.canScrollBackward)
                if (!shouldResize) return Offset.Zero

                val newHeight = (headerPx + delta).coerceIn(minPx, maxPx)
                val consumed = newHeight - headerPx
                headerPx = newHeight
                return Offset(0f, consumed)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = "C'est un match !",
                onBack = { onNavigate(Destination.HomePage) },
                onSettings = { onNavigate(Destination.SettingsPage) }
            )
        },
        bottomBar = { BottomNavBar(onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(nestedScrollConnection)
        ) {
            // En-tête : pleine largeur, hauteur pilotée par le scroll.
            // Pas de calcul de ratio avec intrinsicSize : il vaut NaN tant que la photo
            // distante n'est pas chargée, ce qui faisait planter aspectRatio.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { headerPx.toDp() })
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = matchedUser?.photo),
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
                        text = matchedUser?.let { "${it.prenom} ${it.nom}" } ?: "Utilisateur",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = matchedUser?.localisation ?: "Localisation inconnue",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                if (matchedUser == null) {
                    item { Text(text = "Chargement du profil...") }
                } else {
                    item {
                        Text(
                            text = "Vous avez matché avec ${matchedUser.prenom} !",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    item {
                        Text(
                            text = "${matchedUser.nombreIngredients} ingrédients dans son frigo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(ingredients) { ingredient ->
                        IngredientItem(
                            ingredient = ingredient,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
        }
    }
}