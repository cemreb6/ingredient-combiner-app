package com.example.dinnerforyou.Adapters

import androidx.recyclerview.widget.DiffUtil

class IngredientHeadersDiffItemCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) =
        (oldItem == newItem)

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        (oldItem == newItem)

}