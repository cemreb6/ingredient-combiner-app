package com.example.dinnerforyou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dinnerforyou.Database.IngredientDao
import com.example.dinnerforyou.Fragments.addedItems
import com.example.dinnerforyou.R

class IngredientListViewModel (val dao: IngredientDao, val type:String) : ViewModel() {
    val list = dao.getlistofIngredients(type)

    private val _elist: MutableLiveData<List<String>> = MutableLiveData()
    val elist: LiveData<List<String>>
        get() = _elist

    init {
        var elist = mutableListOf<String>()
        elist.add(" ")
        _elist.postValue(elist)
    }

    fun setImage(type: String): Int {
        var drawable: Int = 0
        if (type == "vegetable") {
            drawable = R.drawable.vegetable
        } else if (type == "meat") {
            drawable = R.drawable.meats
        } else if (type == "fruit") {
            drawable = R.drawable.fruit
        } else if (type == "fish") {
            drawable = R.drawable.fish
        } else if (type == "grains") {
            drawable = R.drawable.grainsbeansandnuts
        } else if (type == "dairy") {
            drawable = R.drawable.dairies
        }
        return drawable
    }

    fun setTitle(type: String): String {
        var title = ""
        if (type == "vegetable") {
            title = "VEGETABLES"
        } else if (type == "meat") {
            title = "MEATS"
        } else if (type == "fruit") {
            title = "FRUITS"
        } else if (type == "fish") {
            title = "FISH AND SEAFOODS"
        } else if (type == "grains") {
            title = "GRAINS & BEANS & NUTS"
        } else if (type == "dairy") {
            title = "DAIRIES"
        }
        return title
    }

    fun add(names: MutableList<String>) {
        for (i in names) {
            if (!addedItems._items.contains(i)) {
                addedItems._items.add(i)
            }
        }
    }
}