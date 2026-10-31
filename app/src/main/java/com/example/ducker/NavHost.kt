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
import com.example.ducker.ui.users.UsersPage


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    viewModel: DuckerViewModel,   // <- à ajouter


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

            // 👇 Navigation 3 te donne accès à la "destination" cliquée ici !
            entry<Destination.FrigoUser> { destination ->
                // 1. Récupère ton repository (soit depuis viewModel.repository, soit depuis ta DB/App)
                val repository = viewModel.repository // (Vérifie si ton DuckerViewModel expose son repository, ou passe-le en paramètre du NavHost)

                // 2. Utilise la Factory que tu as créée dans UserFrigoViewModel
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
                    viewModel = viewModel,   // <- à ajouter
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }
            entry<Destination.MatchsPage> {
//                MatchsPage();
            }
            entry<Destination.DiscoversPage> {
//                DiscoversPage();
            }

        }
    )
}