package com.example.dinnerforyou.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient:Ingredient)
    @Update
    suspend fun update(ingredient:Ingredient)
    @Delete
    suspend fun delete(ingredient:Ingredient)

    @Query("SELECT name from ingredient_table where ingredient_type='fruit'")
     fun getFruits():LiveData<List<String>>

    @Query("SELECT name from ingredient_table where ingredient_type='meat'")
    fun getMeats():LiveData<List<String>>

    @Query("SELECT name from ingredient_table where ingredient_type='vegetable'")
     fun getVegetables():LiveData<List<String>>

    @Query("SELECT name from ingredient_table where ingredient_type='fish'")
     fun getfishAndSeafoods():LiveData<List<String>>

    @Query("SELECT name from ingredient_table where ingredient_type='grain'")
     fun getGrainsBeansAndNuts():LiveData<List<String>>

    @Query("SELECT name from ingredient_table where ingredient_type='dairy'")
    fun getDairies():LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list:MutableList<Ingredient>)

    @Query("SELECT DISTINCT ingredient_type from ingredient_table")
    suspend fun getTypes():List<String>?

    @Query("SELECT name from ingredient_table where ingredient_type=:type")
    fun getlistofIngredients(type:String):LiveData<List<String>>

    @Query("SELECT recipeId from recipe_ingredients_table where ingredient_name=:ingredient_name")
    suspend fun findIngredientsRecipes(ingredient_name:String) : List<Long>?

    @Query("select recipeId from recipe_ingredients_table where (ingredient_name=:ingredient_name and recipeId=:recipe_id)")
    suspend fun findRecipeIngredients(ingredient_name: String,recipe_id:Long) : Long?

    @Query("SELECT * from recipe_table where recipe_id=:id")
    suspend fun findRecipeByID(id: Long): Recipe?
}
