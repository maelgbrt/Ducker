package com.example.ducker.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ducker.domain.data.User
import com.example.ducker.domain.data.Frigo

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val nom: String,
    val prenom: String,
    val localisation: String,
    val photo: String,
    val nombreIngredients: Int
) {


    // Convertit l'entité Room en objet du Domaine
    fun toDomain(frigo: Frigo): User {
        return User(
            id = id,
            nom = nom,
            prenom = prenom,
            localisation = localisation,
            photo = photo,
            nombreIngredients = nombreIngredients,
            frigo = frigo,
            likes = emptyList(),
            dislikes = emptyList()
        )
    }

    companion object {
        // Convertit un objet du Domaine en Entité Room

        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                nom = user.nom,
                prenom = user.prenom,
                localisation = user.localisation,
                photo = user.photo,
                nombreIngredients = user.nombreIngredients
            )
        }
    }
}