package com.example.ducker.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.ducker.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Upsert
    suspend fun upsertIngredients(ingredients: List<IngredientEntity>)

    @Query("SELECT * FROM ingredients WHERE frigoId = :frigoId")
    fun getIngredientsForFrigo(frigoId: String): Flow<List<IngredientEntity>>
}