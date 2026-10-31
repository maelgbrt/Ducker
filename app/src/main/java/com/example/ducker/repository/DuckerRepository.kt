package com.example.ducker.repository

import com.example.ducker.domain.data.Frigo
import com.example.ducker.local.DuckerDatabase
import com.example.ducker.local.entity.FrigoEntity
import com.example.ducker.local.entity.IngredientEntity
import com.example.ducker.local.entity.UserEntity
import com.example.ducker.remote.FoodApi
import com.example.ducker.remote.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DuckerRepository(
    private val api: FoodApi,
    private val db: DuckerDatabase
) {
    private val userDao = db.userDao()
    private val frigoDao = db.frigoDao()
    private val ingredientDao = db.ingredientDao()

    // Fonction pour synchroniser les données de l'API vers Room
    suspend fun refreshData() {
        try {
            // 1. Récupérer les données depuis l'API Ktor
            val response = api.fetchData() // (ou ta fonction pour récupérer l'API)

            // 2. Parcourir et sauvegarder dans Room
            for (remoteUser in response.users) {
                // Sauvegarde du User
                val userEntity = UserEntity.fromDomain(remoteUser.toDomain())
                userDao.upsertUser(userEntity)

                // Sauvegarde du Frigo associé s'il existe
                remoteUser.frigo?.let { remoteFrigo ->
                    val frigoEntity = FrigoEntity.fromDomain(remoteFrigo.toDomain(), remoteUser.id)
                    frigoDao.upsertFrigo(frigoEntity)

                    // Sauvegarde des Ingrédients du frigo
                    remoteFrigo.ingredients?.let { remoteIngs ->
                        val ingredientEntities = remoteIngs.mapIndexed { index, remoteIng ->
                            IngredientEntity.fromDomain(
                                ingredient = remoteIng.toDomain(),
                                ingredientId = "${remoteFrigo.id}_ing_$index",
                                frigoId = remoteFrigo.id
                            )
                        }
                        ingredientDao.upsertIngredients(ingredientEntities)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Gérer l'erreur réseau ici (ex: pas de connexion)
        }
    }

    // 1. Récupérer l'utilisateur depuis Room en temps réel
    fun getUser(userId: String) = userDao.getUserById(userId)

    fun getUser() = userDao.getAllUsers()
    // 2. Récupérer le frigo de l'utilisateur

    // Récupérer le frigo de l'utilisateur et le convertir pour le Domaine
    fun getFrigo(userId: String) = frigoDao.getFrigoByUserId(userId)
    // 3. Récupérer les ingrédients d'un frigo
    fun getIngredients(frigoId: String) = ingredientDao.getIngredientsForFrigo(frigoId)

}