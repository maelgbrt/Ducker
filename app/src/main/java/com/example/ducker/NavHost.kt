package com.example.ducker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay


@Composable
fun NavHost(modifier: Modifier = Modifier) {
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
            entry<Destination.SettingsPage> {
                SettingsPage(
                    onNavigate = { destination ->
                        backStack.add(destination)
                    }
                )
            }
            entry<Destination.UsersPage> {
//                UsersPage();
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