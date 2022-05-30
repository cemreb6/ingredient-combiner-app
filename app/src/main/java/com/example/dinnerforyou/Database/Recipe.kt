package com.example.dinnerforyou.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var recipe_id:Long =0L,
    var recipe_name:String="",
    var recipe_description:String=""
)
