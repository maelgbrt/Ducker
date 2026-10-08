package com.example.ducker.repository

import com.example.ducker.domain.data.CategorieIngredient
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.local.DuckerDatabaseHolder
import com.example.ducker.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

object IngredientRepository {

    suspend fun setIngredients() {
        DuckerDatabaseHolder.database?.ingredientDao()?.upsert(
            listOf(
                Ingredient("Pomme", "Pomme Gala bio", "15/10/2026", CategorieIngredient.Fruit),
                Ingredient("Carotte", "Carottes en sachet", "20/10/2026", CategorieIngredient.Legume),
                Ingredient("Lait", "Lait demi-écrémé 1L", "12/10/2026", CategorieIngredient.ProduitLaitier),
                Ingredient("Saumon", "Pavé de saumon frais", "09/10/2026", CategorieIngredient.Poisson),
                Ingredient("Riz", "Riz basmati 1kg", "01/06/2027", CategorieIngredient.Epicerie),
            ).map { IngredientEntity.fromIngredient(it) }
        )
    }

    fun getIngredients(): Flow<List<Ingredient>> {
        return DuckerDatabaseHolder.database?.ingredientDao()?.getIngredients()
            ?.map { entities -> entities.map { it.toIngredient() } }
            ?: flowOf(emptyList())
    }
}