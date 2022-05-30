package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.IngredientDao
import com.example.dinnerforyou.IngredientViewModel

class IngredientViewModelFactory(private val dao:IngredientDao)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
            return IngredientViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}