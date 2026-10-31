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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.TestModifierUpdaterLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import android.R.attr.direction
import android.graphics.drawable.Icon
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.IconButton
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchsPage(
    modifier: Modifier = Modifier,
    onNavigate: (Destination) -> Unit,
    //viewModel: FridgeViewModel = viewModel()
) {
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
                    Button(onClick = { onNavigate(Destination.MatchsPage) }) {
                        Text(text = "Explore")
                    }
                    Button(onClick = { onNavigate(Destination.UsersPage) }) {
                        Text(text = "Matchs")
                    }
                }
            }
        }
    ) { innerPadding ->
        var currentIndex by remember { mutableIntStateOf(0)}
        val currentUser = sampleUsers.getOrNull(currentIndex)

        fun onMatch() {
            //TODO
            currentIndex++
        }

        fun onPass() {
            // TODO
            currentIndex++
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            //.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentUser == null) {
                Text(text = "Plus personne avec qui manger (◞‸◟；)")
            } else {
                key(currentUser.id) {
                    SwipeableCard(
                        user = currentUser,
                        onMatch = ::onMatch,
                        onPass = ::onPass,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .aspectRatio(2f / 3f)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = ::onMatch) {Text(text = "Miam !")}
                    Button(onClick = ::onPass) {Text(text = "Bof...")}
                }
            }
        }
    }
}

@Composable
fun SwipeableCard(
    user : MatchUser,
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
                painter = painterResource(id = R.drawable.leading_element),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = user.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = user.location, color = Color.White, fontSize = 14.sp )
            }
        }
    }



}


// données de tests

data class MatchUser(
    val id: Int,
    val name: String,
    val location: String,
    @DrawableRes val imageRes: Int
)

private val sampleUsers = listOf(
    MatchUser(1, "Camille", "Lyon", R.drawable.leading_element),
    MatchUser(2, "Thomas", "Paris", R.drawable.leading_element),
    MatchUser(3, "Inès", "Marseille", R.drawable.leading_element),
    MatchUser(4, "Hugo", "Bordeaux", R.drawable.leading_element)
)

