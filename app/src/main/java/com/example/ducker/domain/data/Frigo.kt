package com.example.ducker.domain.data

data class Frigo(
    val id: String,
    val photo: String,
    val ingredients: List<Ingredient>
) {
    companion object {
        fun empty(): Frigo {
            return Frigo(
                id = "",
                photo = "",
                ingredients = emptyList()
            )
        }
    }
}