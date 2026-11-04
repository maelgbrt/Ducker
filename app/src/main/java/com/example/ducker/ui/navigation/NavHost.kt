package com.example.ducker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.screen.frigo.UserFrigo
import com.example.ducker.ui.screen.like.Like
import com.example.ducker.ui.screen.myfridge.MyFridge
import com.example.ducker.ui.users.UsersPage
import com.example.ducker.ui.viewmodel.LikeViewModel
import com.example.ducker.ui.viewmodel.UserFrigoViewModel

private data class Onglet(
    val destination: Destination,
    val label: String,
    val icone: ImageVector
)

private val onglets = listOf(
    Onglet(Destination.MyFridge, "Mon frigo", Icons.Default.Home),
    Onglet(Destination.UsersPage, "Explorer", Icons.Default.Search),
    Onglet(Destination.LikePage, "Favoris", Icons.Default.Favorite),
)

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
) {
    val backStack = rememberNavBackStack(Destination.MyFridge)

    val goBack: () -> Unit = {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    val ecranActuel = backStack.lastOrNull()

    val barreVisible = onglets.any { it.destination == ecranActuel }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (barreVisible) {
                NavigationBar {
                    onglets.forEach { onglet ->
                        NavigationBarItem(
                            selected = ecranActuel == onglet.destination,
                            onClick = {
                                if (ecranActuel != onglet.destination) {
                                    backStack.clear()
                                    backStack.add(onglet.destination)
                                }
                            },
                            icon = { Icon(onglet.icone, contentDescription = onglet.label) },
                            label = { Text(onglet.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            onBack = goBack,
            entryProvider = entryProvider {

                entry<Destination.MyFridge> {
                    val myFridgeViewModel: UserFrigoViewModel = viewModel(
                        key = "frigo_usr_001",
                        factory = UserFrigoViewModel.Factory(
                            repository = viewModel.repository,
                            userId = "usr_001"
                        )
                    )

                    MyFridge(
                        viewModel = myFridgeViewModel,
                        onBack = goBack
                    )
                }

                entry<Destination.UsersPage> {
                    val likeViewModel: LikeViewModel = viewModel(
                        factory = LikeViewModel.Factory(
                            repository = viewModel.repository,
                            userId = "usr_001"
                        )
                    )

                    UsersPage(
                        viewModel = viewModel,
                        onNavigate = { destination -> backStack.add(destination) },
                        onBack = goBack,
                        likeViewModel = likeViewModel
                    )
                }

                entry<Destination.LikePage> {
                    val likeViewModel: LikeViewModel = viewModel(
                        factory = LikeViewModel.Factory(
                            repository = viewModel.repository,
                            userId = "usr_001"
                        )
                    )

                    Like(
                        viewModel = likeViewModel,
                        onNavigate = { destination -> backStack.add(destination) },
                        onBack = goBack
                    )
                }

                entry<Destination.FrigoUser> { destination ->
                    val userFrigoViewModel: UserFrigoViewModel = viewModel(
                        key = "frigo_${destination.userId}",
                        factory = UserFrigoViewModel.Factory(
                            repository = viewModel.repository,
                            userId = destination.userId
                        )
                    )

                    UserFrigo(
                        userId = destination.userId,
                        viewModel = userFrigoViewModel,
                        onBack = goBack,
                        readOnly = destination.readOnly
                    )
                }

            }
        )
    }
}