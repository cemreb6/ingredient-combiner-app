package com.example.dinnerforyou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.IngredientDao
import com.example.dinnerforyou.Database.Recipe
import com.example.dinnerforyou.Fragments.addedItems
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecipeCombinationViewModel(private val dao: IngredientDao) : ViewModel() {



    private var _matchingRecipes = MutableLiveData<List<Recipe>>()
    val matchingRecipes get() = _matchingRecipes

    private val _recipeFoundedChannel = Channel<Boolean>()
    val recipeFoundedChannel = _recipeFoundedChannel.receiveAsFlow()

    private var _onNavigate = MutableLiveData(false)
    val onNavigate: LiveData<Boolean>
        get() = _onNavigate

    fun findRecipes() {
        viewModelScope.launch {
            val ids = dao.findIngredientsRecipes(addedItems._items[0])
            var recipeList= mutableListOf<Recipe>()
            if (!ids.isNullOrEmpty()) {
                for(i in ids){
                    var isFounded=true
                    for(j in addedItems._items){
                        var id=dao.findRecipeIngredients(j,i)
                        if(id ==i ){

                        }
                        else{
                            isFounded=false
                            break
                        }
                    }
                    if(isFounded){
                        var recipe=dao.findRecipeByID(i)
                        if (recipe != null) {
                            recipeList.add(recipe)
                        }
                    }
                }
            }
            if(!recipeList.isNullOrEmpty()){
                _matchingRecipes.postValue(recipeList)
            }
            _recipeFoundedChannel.send(!recipeList.isNullOrEmpty())
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