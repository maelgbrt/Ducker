package com.example.ducker.ui.feature.matchs

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.layout.TestModifierUpdaterLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ducker.Destination
import com.example.ducker.R
//import com.example.ducker.ui.feature.fridge.FridgeViewModel
import com.example.ducker.ui.theme.primaryLight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.annotation.DrawableRes
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
//import android.R.attr.direction
//import android.graphics.drawable.Icon
import com.example.ducker.ui.theme.errorContainerLight
import com.example.ducker.ui.theme.primaryContainerLight
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
/*import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp*/
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Brush
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.components.BottomNavBar
import com.example.ducker.ui.components.DuckerTopBar
import com.example.ducker.local.entity.UserEntity
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchsPage(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
    onNavigate: (Destination) -> Unit,
) {
    val allUsers by viewModel.users.collectAsState()
    val users = allUsers.filter { it.id != "user_001" }
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentUser = users.getOrNull(currentIndex)

    val onMatch: () -> Unit = {
        currentUser?.let { user ->
            viewModel.registerLikeOrDislike(targetUserId = user.id, isLike = true)
            onNavigate(Destination.UsersPage(userId = user.id))
        }
    }

    val onPass: () -> Unit = {
        currentUser?.let { user ->
            viewModel.registerLikeOrDislike(targetUserId = user.id, isLike = false)
        }
        currentIndex++
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DuckerTopBar(
                title = "Explore",
                onBack = { onNavigate(Destination.FridgePage) },
                onSettings = { onNavigate(Destination.SettingsPage) }
            )
        },
        bottomBar = { BottomNavBar(isDarkMode = viewModel.isDarkMode, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentUser == null) {
                Text(text = "Plus personne avec qui manger (◞‸◟；)")
            } else {
                // Une clé par utilisateur : chaque carte repart avec un état de swipe neuf
                key(currentUser.id) {
                    SwipeableCard(
                        user = currentUser,
                        onMatch = onMatch,
                        onPass = onPass,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .aspectRatio(2f / 3f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = onMatch) { Text(text = "Miam !") }
                    Button(onClick = onPass) { Text(text = "Bof...") }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCard(
    user: UserEntity,
    onMatch: () -> Unit,
    onPass: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        onDismiss = { direction ->
            when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> onMatch()
                SwipeToDismissBoxValue.EndToStart -> onPass()
                SwipeToDismissBoxValue.Settled -> Unit
            }
        },
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                targetValue = when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> primaryContainerLight
                    SwipeToDismissBoxValue.EndToStart -> errorContainerLight
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 24.dp),
                contentAlignment = when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                    else -> Alignment.CenterEnd
                }
            ) {
                when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Match",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    SwipeToDismissBoxValue.EndToStart -> Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Passer",
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                    SwipeToDismissBoxValue.Settled -> Unit
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.photo),
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
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "${user.prenom} ${user.nom}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = user.localisation, color = Color.White, fontSize = 14.sp )
            }
        }
    }
}