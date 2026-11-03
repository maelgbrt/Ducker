package com.example.ducker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ducker.data.local.entity.UserEntity
import com.example.ducker.data.local.entity.UserLikeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {

    @Upsert
    suspend fun insertOrUpdateLike(like: UserLikeEntity)

    // Récupérer les noms de ceux qui ont liké
    // Ceux que userId a likés
    @Query("""
    SELECT u.* FROM users u 
    INNER JOIN user_likes l ON u.id = l.targetUserId 
    WHERE l.userId = :userId AND l.isLike = 1
""")
    fun getLikedUsers(userId: String): Flow<List<UserEntity>>

    @Query("""
    SELECT u.* FROM users u 
    INNER JOIN user_likes l ON u.id = l.targetUserId 
    WHERE l.userId = :userId AND l.isLike = 0
""")
    fun getDislikedUsers(userId: String): Flow<List<UserEntity>>

    @Query("SELECT targetUserId FROM user_likes WHERE userId = :currentUserId")
    fun getVotedTargetUserIds(currentUserId: String): Flow<List<String>>
}