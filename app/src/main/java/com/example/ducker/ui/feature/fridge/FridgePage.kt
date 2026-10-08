package com.example.ducker.ui.feature.fridge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.Destination
import com.example.ducker.domain.data.Ingredient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFridge(
    onNavigate: (Destination) -> Unit,
    viewModel: FridgeViewModel = viewModel()
) {
    val ingredients by viewModel.ingredients.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mes ingrédients") },
                navigationIcon = {
                    TextButton(onClick = { onNavigate(Destination.HomePage) }) {
                        Text(text = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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