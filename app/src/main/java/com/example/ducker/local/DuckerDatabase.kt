package com.example.ducker.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ducker.local.dao.FrigoDao
import com.example.ducker.local.dao.IngredientDao
import com.example.ducker.local.dao.UserDao
import com.example.ducker.local.entity.FrigoEntity
import com.example.ducker.local.entity.IngredientEntity
import com.example.ducker.local.entity.LikeDislikeTypeConverter
import com.example.ducker.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        FrigoEntity::class,
        IngredientEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(LikeDislikeTypeConverter::class)
abstract class DuckerDatabase : RoomDatabase() {

    // On expose nos 3 DAO
    abstract fun userDao(): UserDao
    abstract fun frigoDao(): FrigoDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        const val DATABASE_NAME = "ducker_database"
    }
}