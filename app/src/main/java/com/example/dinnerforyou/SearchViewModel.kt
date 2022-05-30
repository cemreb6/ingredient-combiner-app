package com.example.dinnerforyou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.Recipe
import com.example.dinnerforyou.Database.UserDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SearchViewModel(val dao: UserDao) : ViewModel() {

    var inputName = MutableLiveData<String>("")

    private val _isRecipeExistChannel = Channel<Boolean>()
    val isRecipeExistChannel = _isRecipeExistChannel.receiveAsFlow()

    private var _foundRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val foundRecipes: LiveData<List<Recipe>>
        get() = _foundRecipes


    private var _onNavigate= MutableLiveData<Boolean>(false)
    val onNavigate:LiveData<Boolean>
    get()=_onNavigate

    fun findRecipe() {
        viewModelScope.launch {
            val recipe = dao.findRecipebyName(inputName.value!!)
            _isRecipeExistChannel.send(!recipe?.recipe_name.isNullOrEmpty())
            if (recipe?.recipe_name == inputName.value) {
                var tempList = mutableListOf<Recipe>()
                tempList.add(recipe!!)
                _foundRecipes.postValue(tempList)
                selectedRecipe.recipeDescription=recipe.recipe_description
                selectedRecipe.recipeId=recipe.recipe_id
                selectedRecipe.recipeName=recipe.recipe_name
            }
        }
    }

    fun onNavigate(id:Long){
        if(id==selectedRecipe.recipeId){
            _onNavigate.value=true
        }
    }
}

object selectedRecipe{
    var recipeName=""
    var recipeId=0L
    var recipeDescription=""
}