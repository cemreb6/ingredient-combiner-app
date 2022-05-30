package com.example.dinnerforyou.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.ProfileViewModel

class ProfileViewModelFactory (private val dao: UserDao)
    : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}