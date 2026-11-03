package com.example.ducker.domain.usecase

import com.example.ducker.domain.model.Ingredient
import com.example.ducker.data.repository.DuckerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetIngredientsUseCase(
    private val repository: DuckerRepository
) {
    operator fun invoke(frigoId: String): Flow<List<Ingredient>> {
        return repository.getIngredients(frigoId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}