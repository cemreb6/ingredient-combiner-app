package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.FavRecipeViewModel

class FavRecipeViewModelFactory (private val dao: UserDao)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavRecipeViewModel::class.java)) {
            return FavRecipeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}