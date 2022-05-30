package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.IngredientDao
import com.example.dinnerforyou.IngredientListViewModel

class IngredientListViewModelFactory (private val dao: IngredientDao, private val type: String)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientListViewModel::class.java)) {
            return IngredientListViewModel(dao,type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}