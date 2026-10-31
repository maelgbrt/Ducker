package com.example.ducker.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ducker.local.entity.FrigoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FrigoDao {
    @Upsert
    suspend fun upsertFrigo(frigo: FrigoEntity)

    @Query("SELECT * FROM frigos WHERE userId = :userId")
    fun getFrigoByUserId(userId: String): Flow<FrigoEntity?>
}