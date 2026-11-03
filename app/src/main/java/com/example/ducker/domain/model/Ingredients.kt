package com.example.ducker.domain.model

data class Ingredient(
    val nomIngredients: String,
    val descriptionIngredients: String,
    val datePeremptionIngredients: String,
    val categorieIngredient: CategorieIngredient
)