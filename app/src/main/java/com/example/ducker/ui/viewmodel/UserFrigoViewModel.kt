package com.example.ducker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ducker.data.local.entity.FrigoEntity
import com.example.ducker.data.local.entity.FrigoWithIngredients
import com.example.ducker.data.local.entity.UserLikeEntity
import com.example.ducker.data.repository.DuckerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.String

class UserFrigoViewModel(
    private val repository: DuckerRepository,
    private val userId: String // On passe l'ID directement ici
) : ViewModel() {

    val frigoWithIngredients: StateFlow<FrigoWithIngredients?> = repository.getFrigoWithIngredient(userId)
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