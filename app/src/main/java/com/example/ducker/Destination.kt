package com.example.ducker

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable // 👈 Indispensable aussi sur l'interface parent
sealed interface Destination : NavKey {
    @Serializable
    data object Home : Destination

    @Serializable
    data object Page2 : Destination
}