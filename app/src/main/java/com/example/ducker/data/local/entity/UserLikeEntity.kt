package com.example.ducker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_likes")
data class UserLikeEntity(
    @PrimaryKey (autoGenerate = true) val id: Long = 0,
    val userId : String,
    val targetUserId : String,
    val isLike : Boolean
){

}