package com.example.ducker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ducker.domain.data.LikeDislike
import com.example.ducker.local.entity.UserEntity
import com.example.ducker.repository.DuckerRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DuckerViewModel(
    val repository: DuckerRepository
) : ViewModel() {

    val users: StateFlow<List<UserEntity>> = repository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        refresh()
    }

    private val _likesState = MutableStateFlow<List<LikeDislike>>(
        listOf(
            LikeDislike(proprioUser = "user_002", targetUser = "user_001")
        )
    )
    val likesState: StateFlow<List<LikeDislike>> = _likesState

    fun registerLikeOrDislike(targetUserId: String, isLike: Boolean) {
        viewModelScope.launch {
            if (isLike) {
                val newLike = LikeDislike(proprioUser = "user_001", targetUser = targetUserId)
                _likesState.value = _likesState.value + newLike
            }
        }
    }
    fun checkMutualMatch(targetUserId: String): Boolean {
        val allUsersList = users.value
        val targetUser = allUsersList.find { it.id == targetUserId }

        // Vérifie si l'utilisateur cible a un like dans son JSON qui cible "user_001"
        return targetUser?.likes?.any { it.targetUser == "user_001" } == true
    }
    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refreshData()
                _error.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Erreur de synchronisation"
            }
        }
    }

    class Factory(
        private val repository: DuckerRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DuckerViewModel(repository) as T
        }
    }
}