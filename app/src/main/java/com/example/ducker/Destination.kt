package com.example.ducker

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable // 👈 Indispensable aussi sur l'interface parent
sealed interface Destination : NavKey {
//    annotation class Users(val value: Any)

    @Serializable
    data object HomePage : Destination

    @Serializable
    data object SettingsPage : Destination


    @Serializable
    data object DiscoversPage : Destination

    @Serializable
    data object UsersPage : Destination

    @Serializable
    data object MatchsPage : Destination
}