package com.example.ducker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.domain.usecase.GetIngredientsUseCase
import com.example.ducker.local.entity.FrigoEntity
import com.example.ducker.repository.DuckerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserFrigoViewModel(
    private val repository: DuckerRepository,
    private val userId: String // On passe l'ID directement ici
) : ViewModel() {
    private val getIngredientsUseCase = GetIngredientsUseCase(repository)

    val frigo: StateFlow<FrigoEntity?> = repository.getFrigo(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    @OptIn(ExperimentalCoroutinesApi::class)
    val ingredients: StateFlow<List<Ingredient>> = frigo
        .flatMapLatest { currentFrigo ->
            if (currentFrigo == null) {
                flowOf(emptyList<Ingredient>())
            } else {
                repository.getIngredients(currentFrigo.id)
                    .map { entities -> entities.map { it.toDomain() } }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    // La Factory propre et facile
    class Factory(
        private val repository: DuckerRepository,
        private val userId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserFrigoViewModel(repository, userId) as T
        }
    }
}