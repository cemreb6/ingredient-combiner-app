package com.example.dinnerforyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.User
import com.example.dinnerforyou.Database.UserDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(val dao: UserDao):ViewModel() {
    var email=""
    var password=""
    var userName=""
    var user:User=User()

    private val _isUserExistChannel = Channel<Boolean>()
    val isUserExist = _isUserExistChannel.receiveAsFlow()

    private val _getUserName = Channel<Boolean>()
    val getUserName = _getUserName.receiveAsFlow()

    private val _isUpdateSuccesfull = Channel<Boolean>()
    val isUpdateSuccesfull = _isUpdateSuccesfull.receiveAsFlow()

    fun isBlanksFilled():Boolean{
        if(email.isNullOrBlank() || password.isNullOrBlank()){
            return false
        }
        return true
    }
    fun checkAccountValidity(){
        viewModelScope.launch {
            val username= dao.findAccount(email,password)
            _getUserName.send(!username.isNullOrEmpty())
            if(!username.isNullOrEmpty()){
                userName=username
            }
        }
    }

    fun findUser(){
        viewModelScope.launch {
            val tempUser= dao.getUserByEmail(email)
            _isUserExistChannel.send(tempUser?.email==email)
            if(tempUser?.email==email){
                user=tempUser
            }
        }
    }

    fun resetPassword() {
        viewModelScope.launch {

            val tempUser= dao.getUserByEmail(email)
            (if (tempUser != null) {
                tempUser.password=password
                    dao.update(tempUser)
                }
            )
            _isUpdateSuccesfull.send(true)
        }
    }
}