package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.RecipeDetailsViewModel

class RecipeDetailsViewModelFactory (private val dao: UserDao)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)) {
            return RecipeDetailsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}