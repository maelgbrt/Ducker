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

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,
) {
    val backStack = rememberNavBackStack(Destination.HomePage)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Destination.HomePage> {
                HomePage(
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }

            entry < Destination.IngredientAjout>{
                IngredientAjout (
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }

            entry<Destination.FrigoUser> { destination ->
                // Récupération repository
                val repository = viewModel.repository

                // Utilisation Factory
                val userFrigoViewModel: UserFrigoViewModel = viewModel(
                    factory = UserFrigoViewModel.Factory(
                        repository = repository,
                        userId = destination.userId
                    )
                )

                UserFrigo(
                    onNavigate = { dest -> backStack.add(dest) },
                    userId = destination.userId,
                    viewModel = userFrigoViewModel // On passe le bon viewModel créé avec la factory
                )
            }
//            entry<Destination.SettingsPage> {
//                SettingsPage(
//                    onNavigate = { destination ->
//                        backStack.add(destination)
//                    }
//                )
//            }
//            entry<Destination.MyFridge> {
//                MyFridge(
//                    onNavigate = { destination ->
//                        backStack.add(destination)
//                    }
//                );
//            }
            entry<Destination.UsersPage> {
                UsersPage(
                    viewModel = viewModel,
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }
            entry<Destination.MatchsPage> {
                MatchsPage(
                            onNavigate = { destination ->
                                        backStack.add(destination)
                                    });
            }
            entry<Destination.DiscoversPage> {
//                DiscoversPage();
            }

        }
    )
}