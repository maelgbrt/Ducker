package com.example.ducker.remote

import com.example.ducker.domain.data.*
import kotlinx.serialization.Serializable

@Serializable
data class RemoteApiResponse(
    val users: List<RemoteUser>
)

@Serializable // Correction : on retire @Contextual de la classe
data class RemoteUser(
    val id: String,
    val nom: String,
    val prenom: String,
    val localisation: String?,
    val photo: String?,
    val nombre_ingredients: Int,
    val frigo: RemoteFrigo?,
    val likes: List<LikeDislike>? = null,
    val dislikes: List<LikeDislike>? = null
)

@Serializable
data class RemoteFrigo(
    val id: String,
    val photo: String?,
    val ingredients: List<RemoteIngredient>?
)

@Serializable
data class RemoteIngredient(
    val id: String,
    val nom: String,
    val description: String?,
    val date_peremption: String?,
    val categorie: String?
)

// --- Fonctions de mapping (conversion vers ce qui est en local ) ---

fun RemoteUser.toDomain(): User {
    return User(
        id = id,
        nom = nom,
        prenom = prenom,
        localisation = localisation ?: "",
        photo = photo ?: "",
        nombreIngredients = nombre_ingredients,
        frigo = frigo?.toDomain() ?: Frigo.empty(),
        likes = likes ?: emptyList(),       // Récupère correctement les likes du JSON
        dislikes = dislikes ?: emptyList()   // Récupère correctement les dislikes du JSON
    )
}

fun RemoteIngredient.toDomain(): Ingredient {
    val categorieEnum = when (categorie?.lowercase()?.trim()) {
        "fruit", "fruits" -> CategorieIngredient.Fruit
        "legume", "legumes", "légume", "légumes" -> CategorieIngredient.Legume
        "viande", "viandes" -> CategorieIngredient.Viande
        "poisson", "poissons" -> CategorieIngredient.Poisson
        "produit_laitier", "produits laitiers", "produits frais" -> CategorieIngredient.ProduitLaitier
        "epicerie", "épicerie" -> CategorieIngredient.Epicerie
        "boisson", "boissons" -> CategorieIngredient.Boisson
        else -> CategorieIngredient.AUTRE
    }

    return Ingredient(
        nomIngredients = nom,
        descriptionIngredients = description ?: "",
        datePeremptionIngredients = date_peremption ?: "",
        categorieIngredient = categorieEnum
    )
}

fun RemoteFrigo.toDomain(): Frigo {
    return Frigo(
        photo = photo ?: "",
        id = id,
        ingredients = ingredients?.map { it.toDomain() } ?: emptyList()
    )
}