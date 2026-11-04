package com.example.ducker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ducker.data.local.entity.FrigoWithIngredients
import com.example.ducker.data.local.entity.IngredientEntity
import com.example.ducker.data.local.entity.UserLikeEntity
import com.example.ducker.data.repository.DuckerRepository
import com.example.ducker.domain.model.CategorieIngredient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class UserFrigoViewModel(
    private val repository: DuckerRepository,
    private val userId: String
) : ViewModel() {

    val frigoWithIngredients: StateFlow<FrigoWithIngredients?> =
        repository.getFrigoWithIngredient(userId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun vote(currentUserId: String, isLike: Boolean) {
        viewModelScope.launch {
            repository.saveLike(
                UserLikeEntity(
                    userId = currentUserId,
                    targetUserId = userId,
                    isLike = isLike
                )
            )
        }
    }


    fun deleteIngredient(ingredientId: String) {
        viewModelScope.launch {
            repository.deleteIngredient(ingredientId)
        }
    }
    fun addIngredient(
        nom: String,
        description: String,
        datePeremption: String,
        categorie: CategorieIngredient
    ) {
        viewModelScope.launch {
            val frigoId = frigoWithIngredients.value?.frigo?.id
                ?: repository.ensureFrigo(userId)

            Log.d("DUCKER_ADD", "Ajout dans le frigo $frigoId")

            repository.addIngredient(
                IngredientEntity(
                    id = UUID.randomUUID().toString(),
                    frigoId = frigoId,
                    nomIngredients = nom.trim(),
                    descriptionIngredients = description.trim(),
                    datePeremptionIngredients = datePeremption.trim(),
                    categorieIngredient = categorie.name
                )
            )
            Log.d("DUCKER_ADD", "Ingrédient inséré")
        }
    }
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