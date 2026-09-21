package com.example.ducker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Destination.Home)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Destination.Home> {
                HelloWorld(
                    onPage2Click = {
                        backStack.add(Destination.Page2)
                    }
                )
            }
            entry<Destination.Page2> {
                Page2(
                    onBackClick = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    }
                )
            }
        }
    )
}