package com.example.ducker.domain.data

data class User(
    val id: String,
    val nom: String,
    val prenom: String,
    val localisation: String,
    val photo: String,
    val nombreIngredients: Int,
    val frigo: Frigo,
    val likes: List<LikeDislike> = emptyList(),
    val dislikes: List<LikeDislike> = emptyList()
) {
    // Propriété calculée pour le nom complet
    val name: String
        get() = "$prenom $nom"

    // Propriété pour l'email (si tu veux qu'il soit optionnel ou généré)
    val email: String
        get() = "${prenom.lowercase()}.${nom.lowercase()}@example.com"
}