package com.example.dinnerforyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinnerforyou.Database.FavoriteRecipes
import com.example.dinnerforyou.Database.UserDao
import com.example.dinnerforyou.Fragments.userInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(val dao: UserDao) : ViewModel() {
    var ingredients = dao.findIngredients(selectedRecipe.recipeId)
    var description = dao.findDescription(selectedRecipe.recipeId)

    private var _isRecipeExistChannel = Channel<Boolean>()
    val isRecipeExistChannel = _isRecipeExistChannel.receiveAsFlow()

    fun setIngredientList(): String {
        var list = ""

        for (i in ingredients.value!!) {
            list += i + "\n"
        }
        return list
    }


    fun checkFavList() {
        viewModelScope.launch {
            val recipeID = dao.getFavRecipe(userInfo.userEmail, selectedRecipe.recipeId)
            var isExist = false
            if (recipeID != null && !recipeID.equals(0)) {
                isExist = true
            }
            _isRecipeExistChannel.send(isExist)
        }
    }

    fun addFavList() {
        viewModelScope.launch {
            var favRecipe = FavoriteRecipes()
            favRecipe.email = dao.getEmail(userInfo.userEmail)!!
            favRecipe.recipe_id = dao.getRecipeID(selectedRecipe.recipeName)!!
            dao.insertFavRecipe(favRecipe)
        }
    }


}