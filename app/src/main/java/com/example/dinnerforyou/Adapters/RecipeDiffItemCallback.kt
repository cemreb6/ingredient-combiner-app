package com.example.dinnerforyou.Adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.dinnerforyou.Database.Recipe

class RecipeDiffItemCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
        (oldItem.recipe_id == newItem.recipe_id)

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
        (oldItem == newItem)

}
