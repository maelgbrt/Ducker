package com.example.ducker.data.repository

import android.util.Log
import com.example.ducker.data.local.DuckerDatabase
import com.example.ducker.data.local.dao.LikeDao
import com.example.ducker.data.local.entity.FrigoEntity
import com.example.ducker.data.local.entity.IngredientEntity
import com.example.ducker.data.local.entity.UserEntity
import com.example.ducker.data.local.entity.UserLikeEntity
import com.example.ducker.data.remote.FoodApi
import com.example.ducker.data.remote.toDomain

class DuckerRepository(
    private val api: FoodApi,
    private val db: DuckerDatabase
) {
    private val userDao = db.userDao()
    private val frigoDao = db.frigoDao()
    private val ingredientDao = db.ingredientDao()
    private val likeDao = db.likeDao()

    suspend fun refreshData() {
        val response = api.fetchData()
        Log.d("DUCKER", "API: ${response.users.size} users")

        for (remoteUser in response.users) {
            val userEntity = UserEntity.fromDomain(remoteUser.toDomain())
            userDao.upsertUser(userEntity)

            remoteUser.frigo?.let { remoteFrigo ->
                val frigoEntity = FrigoEntity.fromDomain(remoteFrigo.toDomain(), remoteUser.id)
                frigoDao.upsertFrigo(frigoEntity)

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
        Log.d("DUCKER", "Sync OK")
    }

    fun getUser(userId: String) = userDao.getUserById(userId)

    fun getUser() = userDao.getAllUsers()

    fun getFrigoWithIngredient(userId: String) = frigoDao.getFrigoWithIngredient(userId)
    fun getIngredients(frigoId: String) = ingredientDao.getIngredientsForFrigo(frigoId)
    fun getLikesNames(userId: String) = likeDao.getLikedUsers(userId)
    fun getDislikesNames(userId: String) = likeDao.getDislikedUsers(userId)
    suspend fun saveLike(userLikeEntity: UserLikeEntity) = likeDao.insertOrUpdateLike(userLikeEntity)
    fun getVotedTargetUserIds(currentUserId: String) = likeDao.getVotedTargetUserIds(currentUserId)

    suspend fun addIngredient(ingredient: IngredientEntity) {
        ingredientDao.upsertIngredients(listOf(ingredient))
    }

    suspend fun ensureFrigo(userId: String): String {
        val frigoId = "frigo_$userId"
        frigoDao.upsertFrigo(
            FrigoEntity(
                id = frigoId,
                userId = userId,
                photo = ""
            )
        )
        return frigoId
    }

    suspend fun deleteIngredient(ingredientId: String) {
        ingredientDao.deleteIngredient(ingredientId)
    }
}