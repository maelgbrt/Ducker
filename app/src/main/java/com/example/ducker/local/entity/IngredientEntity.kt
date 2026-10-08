package com.example.ducker.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ducker.domain.data.CategorieIngredient
import com.example.ducker.domain.data.Ingredient

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val nomIngredients: String,
    val descriptionIngredients: String,
    val datePeremptionIngredients: String,
    val categorieIngredient: String
) {


    // transforme une entité de la base de données au modele Ingredient /domain/data/Ingredient
    fun toIngredient(): Ingredient {
        return Ingredient(
            nomIngredients = nomIngredients,
            descriptionIngredients = descriptionIngredients,
            datePeremptionIngredients = datePeremptionIngredients,
            categorieIngredient = CategorieIngredient.entries
                .firstOrNull { it.raw == categorieIngredient }
                ?: CategorieIngredient.Autre
        )
    }

    //Chemin inverse transformet le modele Ingredient de data en entité pr la base de données
    companion object {
        fun fromIngredient(ingredient: Ingredient): IngredientEntity {
            return IngredientEntity(
                nomIngredients = ingredient.nomIngredients,
                descriptionIngredients = ingredient.descriptionIngredients,
                datePeremptionIngredients = ingredient.datePeremptionIngredients,
                categorieIngredient = ingredient.categorieIngredient.raw
            )
        }
    }
}