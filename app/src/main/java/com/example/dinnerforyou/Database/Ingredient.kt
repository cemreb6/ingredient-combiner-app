package com.example.dinnerforyou.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="ingredient_table")
data class Ingredient(
    @PrimaryKey
    var name:String="",
    var ingredient_type: String
)
