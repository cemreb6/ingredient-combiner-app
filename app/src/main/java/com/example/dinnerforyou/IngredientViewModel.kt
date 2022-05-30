package com.example.dinnerforyou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.IngredientDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class IngredientViewModel(val dao: IngredientDao) : ViewModel() {

    private var _typesChannel = Channel<Boolean>()
    val typesChannel = _typesChannel.receiveAsFlow()

    var typesList: MutableLiveData<List<String>> = MutableLiveData()

    private var _selectedType: MutableLiveData<String> = MutableLiveData()
    val selectedType: LiveData<String>
        get() = _selectedType

    fun setTypesList() {
        viewModelScope.launch {
            val list = dao.getTypes()
            _typesChannel.send(!list.isNullOrEmpty())
            if (!list.isNullOrEmpty()) {
                typesList.postValue(list)

            }
        }
    }

    fun onClicked(type: String) {
        _selectedType.value = type
    }



}

