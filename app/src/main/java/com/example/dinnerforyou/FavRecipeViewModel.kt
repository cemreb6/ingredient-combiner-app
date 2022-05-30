package com.example.dinnerforyou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.Recipe
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.Fragments.userInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FavRecipeViewModel(val dao: UserDao) : ViewModel() {

    private var _favIds= mutableListOf<Long>()
    val favIds: List<Long>
    get()=_favIds

    private var _favList= mutableListOf<Recipe>()

    private var _onNavigate = MutableLiveData<Boolean>(false)
    val onNavigate: LiveData<Boolean>
        get() = _onNavigate

    private val _favRecipeChannel = Channel<Boolean>()
    val favRecipeChannel = _favRecipeChannel.receiveAsFlow()

    private var _favorites = MutableLiveData<List<Recipe>>()
    val favorites: LiveData<List<Recipe>>
        get() = _favorites

    fun findRecipes(){
        viewModelScope.launch {
            var list=dao.getFavRecipes(userInfo.userEmail)
            val isExist= !list.isNullOrEmpty()
            if(isExist){
                if (list != null) {
                    _favIds.addAll(list)
                    getFav()
                }
            }
        }
    }

    private fun getFav() {
        viewModelScope.launch {
            for(i in favIds){
                var recipe=dao.findRecipeByID(i)
                _favList.add(recipe!!)
            }
            _favorites.postValue(_favList)
            _favRecipeChannel.send(!_favList.isNullOrEmpty())
        }
    }

    fun onNavigate(id: Long) {
        setSelectedList(id)
        _onNavigate.value = true
    }

    private fun setSelectedList(id: Long) {
        viewModelScope.launch {
            val recipe = dao.findRecipeByID(id)
            selectedRecipe.recipeName = recipe?.recipe_name!!
            selectedRecipe.recipeId = id
            selectedRecipe.recipeDescription = recipe.recipe_description
        }
    }


}


