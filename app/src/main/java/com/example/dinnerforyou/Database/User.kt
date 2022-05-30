package com.example.dinnerforyou.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_table")
data class User(
  var username:String="",
  @PrimaryKey()
  var email:String="",
  var password:String=""
)
