package com.example.ducker.data.remote

import com.example.ducker.domain.model.*
import kotlinx.serialization.Serializable

@Serializable
data class RemoteApiResponse(
    val users: List<RemoteUser>
)

@Serializable
data class RemoteUser(
    val id: String,
    val nom: String,
    val prenom: String,
    val localisation: String?,
    val photo: String?,
    val nombre_ingredients: Int,
    val frigo: RemoteFrigo?
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
        likes = emptyList(),    // Si tu les gères plus tard
        dislikes = emptyList()  // Si tu les gères plus tard
    )
}

fun RemoteIngredient.toDomain(): Ingredient {
    // Conversion sécurisée pour accepter les textes du JSON sans planter
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
        photo = photo ?: "", // Gère le cas où l'URL de la photo est null
        id = id,
        ingredients = ingredients?.map { it.toDomain() } ?: emptyList()
    )
}