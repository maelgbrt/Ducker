package com.example.ducker.ui.feature.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
//import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
/*import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items*/
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
//import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ducker.Destination
import com.example.ducker.ui.DuckerViewModel
/*import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import com.example.ducker.domain.data.Frigo*/
import com.example.ducker.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
/*import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.domain.data.Ingredient
//import com.example.ducker.ui.feature.fridge.FridgeViewModel
import com.example.ducker.ui.feature.fridge.IngredientItem
import com.example.ducker.ui.theme.onPrimaryLight*/
import com.example.ducker.ui.theme.primaryLight
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalDensity
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.ui.feature.settings.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun UsersPage(
    modifier: Modifier = Modifier,
    maxImageSize : Dp = 300.dp,
    minImageSize: Dp = 100.dp,
    viewModel: DuckerViewModel,
    onNavigate : (Destination) -> Unit,)
     {
    val users by viewModel.users.collectAsState()
    val error by viewModel.error.collectAsState()
    //val ingredients by viewModel.ingredients.collectAsStateWithLifecycle()
    var currentImageSize by remember { mutableStateOf(maxImageSize) }
    var imageScale by remember { mutableFloatStateOf(1f) }
         val density = LocalDensity.current
         val maxPx = with(density) { maxImageSize.toPx() }
         val minPx = with(density) { minImageSize.toPx() }

         var headerPx by remember { mutableFloatStateOf(maxPx) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize

                // Constrain the image size within the allowed bounds
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                val consumed = currentImageSize - previousImageSize

                // Calculate the scale for the image
                imageScale = currentImageSize / maxImageSize

                // Return the consumed scroll amount
                return Offset(0f, consumed.value)
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
                .padding(innerPadding)
                //.background(SecondaryPurple)
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { headerPx.toDp() })
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Box( // on met la box dans une lazycolumn pour gérer l'effet de redimension pendant le scroll
                            //modifier = Modifier.fillMaxWidth()
                        ) {
                            val painter = painterResource(id = R.drawable.leading_element)
                            val imageRatio = painter.intrinsicSize.width / painter.intrinsicSize.height
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(imageRatio / 0.6f)
                                    .graphicsLayer {
                                        scaleX = imageScale
                                        scaleY = imageScale
                                        translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                                    }
                            )

                            TextButton(onClick = { onNavigate(Destination.HomePage) }) {
                                Text(text = "Retour")
                            }
                            Column(
                                modifier = modifier.align(Alignment.BottomStart)
                                .padding(horizontal = 8.dp)) {
                                Text(
                                    text = "Nom de l'user",
                                    color = TextPrimary,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Localisation", color = TextPrimary, fontSize = 20.sp
                                )
                            }
                        } // fin box
                    }

                    /*item {
                        Text(
                            text = "Liste des ingrédients",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }*/

                    /*items(items = ingredients) { ingredient ->
                        IngredientItem(
                            ingredient = ingredient,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp)

                        )
                    }*/
                }
            }
        }
    }
}

/*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des utilisateurs",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = users, key = { it.hashCode() }) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = user.photo),
                            contentDescription = "Photo de profil",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                        Text(
                            text = "${user.nom} ${user.prenom}",
                            modifier = Modifier.padding(start = 16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Column(
                            Modifier.fillMaxHeight()
                        ) {
                            Button(onClick = { onNavigate(Destination.FrigoUser(userId = user.id)) }) {
                                Text(text = "Voir Frigo")
                            }
                        }
                    }
                }
            }
        }
    }*/