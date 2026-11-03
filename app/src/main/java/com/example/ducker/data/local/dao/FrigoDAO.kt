package com.example.ducker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ducker.data.local.entity.FrigoEntity
import com.example.ducker.data.local.entity.FrigoWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface FrigoDao {
    @Upsert
    suspend fun upsertFrigo(frigo: FrigoEntity)

    @Query("SELECT * FROM frigos WHERE userId = :userId")
    fun getFrigoWithIngredient(userId: String): Flow<FrigoWithIngredients?>
}