package com.example.ducker.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ducker.local.dao.IngredientDao
import com.example.ducker.local.entity.IngredientEntity

@Database(
    entities = [IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DuckerDatabase : RoomDatabase() {

    abstract fun ingredientDao(): IngredientDao

    companion object {
        private const val DB_NAME = "ducker.db"

        @Volatile
        private var INSTANCE: DuckerDatabase? = null

        fun get(context: Context): DuckerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DuckerDatabase::class.java,
                    DB_NAME
                ).build().also { INSTANCE = it }
            }
        }
    }
}

data object DuckerDatabaseHolder {
    var database: DuckerDatabase? = null

    fun initialize(context: Context) {
        database = DuckerDatabase.get(context)
    }
}