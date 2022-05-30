package com.example.dinnerforyou.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Ingredient::class,Recipe::class,RecipeIngredients::class,FavoriteRecipes::class], version = 6, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val ingredientDao:IngredientDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "recipemaker_database.db"
                    ).createFromAsset("database/recipemaker_database.db")
                       .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}