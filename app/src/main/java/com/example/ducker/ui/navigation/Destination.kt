package com.example.ducker.ui.navigation

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
    data object MyFridge : Destination

    @Serializable
    data object IngredientAjout : Destination
    @Serializable
    data object DiscoversPage : Destination

    @Serializable
    data object UsersPage : Destination

    @Serializable
    data object LikePage : Destination

    @Serializable
    data class FrigoUser(val userId: String) : Destination //on met une class car ya des paramètres

    @Serializable
    data object MatchsPage : Destination
}