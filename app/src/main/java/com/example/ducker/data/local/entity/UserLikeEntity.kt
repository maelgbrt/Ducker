package com.example.ducker.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "user_likes",
    primaryKeys = ["userId", "targetUserId"]
)
data class UserLikeEntity(
    val userId: String,
    val targetUserId: String,
    val isLike: Boolean
)