package com.example.ducker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ducker.data.local.dao.FrigoDao
import com.example.ducker.data.local.dao.IngredientDao
import com.example.ducker.data.local.dao.LikeDao
import com.example.ducker.data.local.dao.UserDao
import com.example.ducker.data.local.entity.FrigoEntity
import com.example.ducker.data.local.entity.IngredientEntity
import com.example.ducker.data.local.entity.UserEntity
import com.example.ducker.data.local.entity.UserLikeEntity

@Database(
    entities = [
        UserEntity::class,
        FrigoEntity::class,
        IngredientEntity::class,
        UserLikeEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class DuckerDatabase : RoomDatabase() {

    // On expose nos 3 DAO
    abstract fun userDao(): UserDao
    abstract fun frigoDao(): FrigoDao
    abstract fun ingredientDao(): IngredientDao

    abstract fun likeDao(): LikeDao

    companion object {
        const val DATABASE_NAME = "ducker_database"
    }
}