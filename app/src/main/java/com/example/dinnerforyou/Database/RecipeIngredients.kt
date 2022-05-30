package com.example.dinnerforyou.Database

import androidx.room.*
@Entity(
    tableName = "recipe_ingredients_table",
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["recipe_id"],
        childColumns = ["recipeId"]
    ),ForeignKey(
        entity = Ingredient::class,
        parentColumns = ["name"],
        childColumns = ["ingredient_name"]
    )]
)
data class RecipeIngredients (

    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,
    val recipeId: Long = 0L,
    val ingredient_name: String = ""

)