package com.example.dinnerforyou

import androidx.lifecycle.ViewModel
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.Fragments.userInfo

class ProfileViewModel(val dao: UserDao): ViewModel() {
    val userName=dao.findUserName(userInfo.userEmail)
}