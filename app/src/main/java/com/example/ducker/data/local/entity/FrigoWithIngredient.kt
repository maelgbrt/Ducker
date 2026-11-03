package com.example.ducker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FrigoWithIngredients(
    @Embedded val frigo: FrigoEntity,
    @Relation(
        parentColumn = "id",      // L'ID du frigo
        entityColumn = "frigoId"  // La clé étrangère dans la table des ingrédients
    )
    val ingredients: List<IngredientEntity>
)