package com.example.ducker.ui.feature.fridge

//import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.style.styleable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.Destination
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.ui.theme.primaryLight
import com.example.ducker.R
import com.example.ducker.ui.theme.onPrimaryLight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.ducker.domain.data.CategorieIngredient
import com.example.ducker.local.entity.UserEntity
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.UserFrigoViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgePage(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
    frigoViewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit,

    ) {
    val users by viewModel.users.collectAsState()
    val frigo by frigoViewModel.frigo.collectAsState()
    val ingredients by frigoViewModel.ingredients.collectAsState()
    val me = users.firstOrNull { it.id == "user_001" }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = "Mon frigo",
                onBack = { onNavigate(Destination.HomePage) },
                onSettings = { onNavigate(Destination.SettingsPage) }
            )
        },
        bottomBar = { BottomNavBar(onNavigate = onNavigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserCard(
                user = me,
                frigoId = frigo?.id?.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    Text(
                        text = "Liste des ingrédients",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(items = ingredients) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = ingredient.nomIngredients,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = ingredient.descriptionIngredients,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "À consommer avant le ${ingredient.datePeremptionIngredients}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = ingredient.categorieIngredient.raw,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun UserCard(
    user: UserEntity?,
    frigoId: String?,
    modifier: Modifier = Modifier
) {
    if (user == null) {
        Text(text = "Chargement du profil...", modifier = modifier)
        return
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.photo),
                contentDescription = "Photo de profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))

            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(primaryLight)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Bonjour ${user.prenom},",
                    color = onPrimaryLight,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                if (frigoId != null) {
                    Text(
                        text = "Frigo $frigoId",
                        color = onPrimaryLight,
                        fontSize = 12.sp
                    )
                }
            /*Button(onClick = {
                Toast.makeText(
                    context,
                    "On veut vraiment partager le profil ?",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Text(text = "Partager")
            }*/
        }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${user.prenom} ${user.nom}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${user.localisation} · ${user.nombreIngredients} ingrédients",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}