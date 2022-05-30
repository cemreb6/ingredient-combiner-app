package com.example.dinnerforyou

import androidx.lifecycle.*
import com.example.dinnerforyou.Database.User
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.Fragments.userInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(val dao: UserDao): ViewModel() {
    var email= ""
    var password=""
    var username=""
    private val _isEmailExistChannel = Channel<Boolean>()
    val isEmailExist = _isEmailExistChannel.receiveAsFlow()

    fun isBlanksFilled():Boolean{
        if(email.isNullOrBlank() || password.isNullOrBlank() || username.isNullOrBlank()){
            return false
        }
        return true
    }

    fun isEmailExist() {
        viewModelScope.launch{
            val isExist=dao.getEmail(email)
            _isEmailExistChannel.send(!isExist.isNullOrEmpty())
       }
    }

    fun createAccount() {
        val user= User()
        user.email= email
        user.password=password
        user.username=username
        userInfo.userName=username
        userInfo.userEmail=email
        viewModelScope.launch {
            dao.insert(user)
        }
    }
}