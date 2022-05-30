package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.SignInViewModel

class SignInViewModelFactory(private val dao: UserDao)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}