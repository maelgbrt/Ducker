package com.example.ducker.ui.feature.fridge

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ducker.Destination
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.local.entity.UserEntity
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.UserFrigoViewModel
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.ui.theme.onPrimaryLight
import com.example.ducker.ui.theme.primaryLight
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgePage(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
    frigoViewModel: UserFrigoViewModel,
    onNavigate: (Destination) -> Unit,

    ) {
    val context = LocalContext.current
    val users by viewModel.users.collectAsState()
    val frigo by frigoViewModel.frigo.collectAsState()
    val ingredients by frigoViewModel.ingredients.collectAsState()
    val me = users.firstOrNull { it.id == "user_001" }

    // Fichier local dans le stockage interne
    val photoFile = remember { File(context.filesDir, "frigo_photo.jpg") }

    // État pour stocker le Bitmap de la session en cours (rafraîchissement immédiat)
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Lanceur de l'appareil photo
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            saveBitmapToInternalStorage(context, bitmap, "frigo_photo.jpg")
            capturedBitmap = bitmap // Met à jour le state pour forcer l'affichage instantané
        }
    }

    // Lanceur de permission de la caméra
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    val openCameraAction = {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                cameraLauncher.launch()
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    // Détermination de la source de l'image à afficher
    val imageModel: Any? = when {
        capturedBitmap != null -> capturedBitmap
        photoFile.exists() -> photoFile
        else -> me?.photo
    }

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
                imageModel = imageModel,
                frigoId = frigo?.id?.toString(),
                onPhotoClick = openCameraAction,
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

// Fonction utilitaire pour enregistrer le bitmap localement
fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, fileName: String) {
    try {
        val file = File(context.filesDir, fileName)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        fos.flush()
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
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
    imageModel: Any?,
    frigoId: String?,
    onPhotoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (user == null) {
        Text(text = "Chargement du profil...", modifier = modifier)
        return
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Photo cliquable pour lancer la caméra
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onPhotoClick)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageModel),
                    contentDescription = "Photo de profil ou du frigo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                if (frigoId != null) {
                    Text(
                        text = "Frigo $frigoId",
                        color = onPrimaryLight,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Nom et localisation sortis de la Row pour occuper toute la largeur proprement
        Text(
            text = "${user.prenom} ${user.nom}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${user.localisation} · ${user.nombreIngredients} ingrédients",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
