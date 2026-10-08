package com.example.ducker.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ducker.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Upsert // Update + Insert
    suspend fun upsert(ingredients: List<IngredientEntity>) // suspend : continue de tourner derrière

    @Query("SELECT * FROM ingredients") // Select Ingredient
    fun getIngredients(): Flow<List<IngredientEntity>>
}