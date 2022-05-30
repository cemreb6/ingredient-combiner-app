package com.example.dinnerforyou.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_recipes_table",
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipe_id"],
        childColumns = ["recipe_id"]
    ), ForeignKey(
        entity = User::class,
        parentColumns = ["email"],
        childColumns = ["email"]
    )]
)
data class FavoriteRecipes(
    @PrimaryKey(autoGenerate=true)
    var id:Long=0L,
    var recipe_id:Long=0L,
    var email: String=""
)
