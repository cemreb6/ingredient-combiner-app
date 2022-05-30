package com.example.dinnerforyou.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User): Int?

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT username from user_table where email=:email and password=:password")
    suspend fun findAccount(email: String, password: String): String?

    @Query("SELECT email from user_table where email=:email")
    suspend fun getEmail(email: String): String?

    @Query("SELECT * from user_table where email=:email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT username from user_table where email=:email")
    fun findUserName(email: String): LiveData<String>

    @Query("SELECT * from recipe_table where recipe_name=:recipe_name")
    suspend fun findRecipebyName(recipe_name: String): Recipe?

    @Query("SELECT * from recipe_table where recipe_id=:id")
    suspend fun findRecipeByID(id: Long): Recipe?

    @Query("SELECT ingredient_name from recipe_ingredients_table where recipeId=:recipeId")
    fun findIngredients(recipeId: Long): LiveData<List<String>>

    @Query("SELECT recipe_description from recipe_table where recipe_id=:id")
    fun findDescription(id: Long): LiveData<String>

    @Insert
    suspend fun insertFavRecipe(recipe: FavoriteRecipes)

    @Query("SELECT recipe_id from recipe_table where recipe_name=:recipeName")
    suspend fun getRecipeID(recipeName: String): Long?

    @Query("SELECT recipe_id from favorite_recipes_table where recipe_id=:id and email=:email")
    suspend fun getFavRecipe(email: String, id: Long): Long?

    @Query("SELECT recipe_id from favorite_recipes_table where email=:email")
    suspend fun getFavRecipes(email: String): List<Long>?



}