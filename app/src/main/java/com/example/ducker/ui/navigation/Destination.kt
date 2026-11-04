package com.example.ducker.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey {

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
    data class FrigoUser(
        val userId: String,
        val readOnly: Boolean = false
    ) : Destination

    @Serializable
    data object MatchsPage : Destination
}