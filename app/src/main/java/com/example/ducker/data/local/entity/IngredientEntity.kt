package com.example.ducker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ducker.domain.model.CategorieIngredient
import com.example.ducker.domain.model.Ingredient

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val id: String,
    val frigoId: String, // Clé pour lier l'ingrédient à son frigo
    val nomIngredients: String,
    val descriptionIngredients: String,
    val datePeremptionIngredients: String,
    val categorieIngredient: String // On stocke l'enum en String dans Room
) {
    // Convertit l'entité Room en objet du Domaine Ingredient
    fun toDomain(): Ingredient {
        return Ingredient(
            nomIngredients = nomIngredients,
            descriptionIngredients = descriptionIngredients,
            datePeremptionIngredients = datePeremptionIngredients,
            categorieIngredient = try {
                CategorieIngredient.valueOf(categorieIngredient)
            } catch (e: Exception) {
                CategorieIngredient.AUTRE // Valeur de repli par sécurité
            }
        )
    }

    companion object {
        fun fromDomain(ingredient: Ingredient, ingredientId: String, frigoId: String): IngredientEntity {
            return IngredientEntity(
                id = ingredientId,
                frigoId = frigoId,
                nomIngredients = ingredient.nomIngredients,
                descriptionIngredients = ingredient.descriptionIngredients,
                datePeremptionIngredients = ingredient.datePeremptionIngredients,
                categorieIngredient = ingredient.categorieIngredient.name
            )
        }
    }
}