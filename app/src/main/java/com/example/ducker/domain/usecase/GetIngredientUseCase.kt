package com.example.ducker.domain.usecase

import com.example.ducker.domain.data.CategorieIngredient
import com.example.ducker.domain.data.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object GetIngredientsUseCase {
    fun invoke(): Flow<List<Ingredient>> {
        return flowOf(
            listOf(
                Ingredient("Pomme", "Pomme Gala bio", "15/10/2026", CategorieIngredient.Fruit),
                Ingredient("Carotte", "Carottes en sachet", "20/10/2026", CategorieIngredient.Legume),
                Ingredient("Lait", "Lait demi-écrémé 1L", "12/10/2026", CategorieIngredient.ProduitLaitier),
                Ingredient("Saumon", "Pavé de saumon frais", "09/10/2026", CategorieIngredient.Poisson),
                Ingredient("Riz", "Riz basmati 1kg", "01/06/2027", CategorieIngredient.Epicerie),
                Ingredient("Pates", "Pâtes Spaghetti 1kg", "01/06/2027", CategorieIngredient.Epicerie),

            )
        )
    }
}