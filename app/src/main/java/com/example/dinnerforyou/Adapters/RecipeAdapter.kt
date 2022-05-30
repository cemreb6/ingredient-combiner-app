package com.example.dinnerforyou.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerforyou.Database.Recipe
import com.example.dinnerforyou.databinding.RecipesBinding

class RecipeAdapter(
    val clickListener: (id: Long) -> Unit
) :
    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(
        RecipeDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecipeViewHolder = RecipeViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class RecipeViewHolder(val binding: RecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }

        fun bind(item: Recipe, clickListener: (id: Long) -> Unit) {
            binding.recipeTitle.text = item.recipe_name
            binding.selectRecipeButton.setOnClickListener {
                clickListener(item.recipe_id)
            }
        }

    }


}