package com.example.ducker.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ducker.domain.data.Frigo
import com.example.ducker.domain.data.Ingredient

@Entity(tableName = "frigos")
data class FrigoEntity(
    @PrimaryKey val id: String,
    val userId: String, // Clé pour lier le frigo à son utilisateur
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