package com.example.ducker.ui.feature.fridge


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ducker.domain.data.Ingredient
import com.example.ducker.domain.usecase.GetIngredientsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FridgeViewModel : ViewModel() {

    val ingredients: StateFlow<List<Ingredient>> =
        GetIngredientsUseCase.invoke()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}