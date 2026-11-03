package com.example.ducker.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.ducker.domain.data.User
import com.example.ducker.domain.data.Frigo
import com.example.ducker.domain.data.LikeDislike
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlin.collections.emptyList

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val nom: String,
    val prenom: String,
    val localisation: String,
    val photo: String,
    val nombreIngredients: Int,
    val likes: List<LikeDislike> = emptyList(),
    val dislikes: List<LikeDislike> = emptyList()
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
            likes = likes,
            dislikes = dislikes
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
                nombreIngredients = user.nombreIngredients,
                likes = user.likes,
                dislikes = user.dislikes
            )
        }
    }
}

// convertir List<LikeDislike> pour Room

class LikeDislikeTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<LikeDislike> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : com.google.gson.reflect.TypeToken<List<LikeDislike>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<LikeDislike>?): String {
        return gson.toJson(list ?: emptyList<LikeDislike>())
    }
}