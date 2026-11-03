package com.example.ducker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.ducker.ui.DuckerViewModel
import com.example.ducker.ui.UserFrigoViewModel
import com.example.ducker.ui.feature.frigo.UserFrigo
import com.example.ducker.ui.feature.ingredientAjout.IngredientAjout
import com.example.ducker.ui.feature.users.UsersPage
import com.example.ducker.ui.feature.matchs.MatchsPage
import com.example.ducker.ui.feature.fridge.FridgePage
import com.example.ducker.ui.feature.settings.SettingsPage

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
) {
    val backStack = rememberNavBackStack(Destination.HomePage)

    val onNavigate: (Destination) -> Unit = { destination ->
        when {
            destination == Destination.HomePage -> {
                while (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
            }
            backStack.lastOrNull() != destination -> backStack.add(destination)
        }
    }

    val onBack: () -> Unit = {
        if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
    }

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {

            entry<Destination.HomePage> {
                HomePage(onNavigate = onNavigate)
            }

            entry<Destination.IngredientAjout> {
                IngredientAjout(onNavigate = onNavigate)
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
                    onNavigate = { dest -> backStack.add(dest) },
                    userId = destination.userId,
                    viewModel = userFrigoViewModel
                )
            }

            entry<Destination.SettingsPage> {
                SettingsPage(onNavigate = onNavigate)
            }

            entry<Destination.FridgePage> {
                val fridgeViewModel: UserFrigoViewModel = viewModel(
                    key = "frigo_user_001",
                    factory = UserFrigoViewModel.Factory(
                        repository = viewModel.repository,
                        userId = "user_001"
                    )
                )
                FridgePage(
                    viewModel = viewModel,
                    frigoViewModel = fridgeViewModel,
                    onNavigate = onNavigate
                )
            }

            entry<Destination.UsersPage> {
                UsersPage(
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
            }

            entry<Destination.MatchsPage> {
                MatchsPage(
                    viewModel = viewModel,
                    onNavigate = onNavigate
                )
            }
        }
    )
}