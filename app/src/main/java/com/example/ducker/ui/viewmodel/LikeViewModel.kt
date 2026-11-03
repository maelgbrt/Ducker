package com.example.ducker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ducker.data.local.entity.UserEntity
import com.example.ducker.data.repository.DuckerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class LikeViewModel (
    private val repository: DuckerRepository,
    private val CurrentuserId: String,
): ViewModel() {

    val likersNames: StateFlow<List<UserEntity>> = repository.getLikesNames("usr_001")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Liste des noms de ceux qui ont disliké
    val dislikersNames: StateFlow<List<UserEntity>> = repository.getDislikesNames("usr_001")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val votedUserIds: StateFlow<List<String>> = repository.getVotedTargetUserIds("usr_001")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    class Factory(
        private val repository: DuckerRepository,
        private val userId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LikeViewModel(repository, userId) as T
        }
    }
}