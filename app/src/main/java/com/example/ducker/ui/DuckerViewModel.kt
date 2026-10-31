package com.example.ducker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refreshData()
                _error.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace() // <-- Regarde dans l'onglet "Logcat" d'Android Studio ce qui s'affiche ici
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