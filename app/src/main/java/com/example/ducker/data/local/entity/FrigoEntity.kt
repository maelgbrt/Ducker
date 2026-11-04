package com.example.ducker.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ducker.domain.model.Frigo
import com.example.ducker.domain.model.Ingredient

@Entity(tableName = "frigos")
data class FrigoEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val photo: String
) {
    // Convertit l'entité Room en objet du Domaine Frigo
    fun toDomain(ingredients: List<Ingredient>): Frigo {
        return Frigo(
            id = id,
            photo = photo,
            ingredients = ingredients
        )
    }

    companion object {
        fun fromDomain(frigo: Frigo, userId: String): FrigoEntity {
            return FrigoEntity(
                id = frigo.id,
                userId = userId,
                photo = frigo.photo
            )
        }
    }
}