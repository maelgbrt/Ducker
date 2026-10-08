package com.example.ducker.domain.usecase

import com.example.ducker.domain.data.CategorieIngredient
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.domain.data.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object GetUsersUseCase {
    fun invoke(): Flow<List<Users>> {
        return flowOf(
            listOf(
                Users("Gaborit","Chambéry","photo1","Maël"),
                Users("Boudjaj","Chambéry","photo2","Hania")
                )
        )
    }
}