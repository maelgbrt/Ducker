package com.example.ducker.ui.feature.fridge

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.style.styleable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.Destination
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.ui.theme.primaryLight
import com.example.ducker.R
import com.example.ducker.ui.theme.onPrimaryLight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFridge(
    modifier: Modifier = Modifier,
    onNavigate: (Destination) -> Unit,
    //viewModel: FridgeViewModel = viewModel()
) {
    //val ingredients by viewModel.ingredients.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Explore") },
                navigationIcon = {
                    TextButton(onClick = { onNavigate(Destination.HomePage) }) {
                        Text(text = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigate(Destination.SettingsPage) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Paramètres"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(0.dp, 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(17.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(primaryLight)
                        .padding(10.dp, 5.dp),
                ) {
                    Button(onClick = { onNavigate(Destination.MyFridge) }) {
                        Text(text = "My Fridge")
                    }
                    Button(onClick = {onNavigate(Destination.MatchsPage)}) {
                        Text(text = "Explore")
                    }
                    Button(onClick = { onNavigate(Destination.UsersPage) }) {
                        Text(text = "Matchs")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserCard(
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
                /*items(items = ingredients) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        modifier = Modifier.fillMaxWidth()
                    )
                }*/
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
    modifier: Modifier = Modifier,
    user: User = userTest
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = user.imageRes),
                contentDescription = null,
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
                    text = "Bonjour ${user.name},",
                    color = onPrimaryLight,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Frigo ${user.id}",
                    color = onPrimaryLight,
                    fontSize = 10.sp
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

        Text(text = "Description", style = MaterialTheme.typography.titleMedium)
        Text(text = user.description, style = MaterialTheme.typography.bodyMedium)
    }
}

// TODO : bons champs
data class User(
    val id: Int,
    val name: String,
    val location: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

private val userTest = User(1, "Camille", "Lyon",
    "Vous savez, moi je ne crois pas qu’il y ait de bonne ou de mauvaise situation.",
    R.drawable.leading_element)