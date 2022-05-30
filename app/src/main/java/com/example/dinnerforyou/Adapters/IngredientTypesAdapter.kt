package com.example.dinnerforyou.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerforyou.R
import com.example.dinnerforyou.databinding.IngredientTypesBinding

class IngredientTypesAdapter(val clickListener: (type: String) -> Unit) :
    ListAdapter<String, IngredientTypesAdapter.IngredientViewHolder>(
        IngredientHeadersDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : IngredientViewHolder = IngredientViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class IngredientViewHolder(val binding: IngredientTypesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): IngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientTypesBinding.inflate(layoutInflater, parent, false)
                return IngredientViewHolder(binding)
            }
        }

        fun bind(item: String, clickListener: (type: String) -> Unit) {
            var title=""

            binding.root.setOnClickListener { clickListener(item) }
            if(item=="vegetable"){
                binding.image.setBackgroundResource(R.drawable.veg_icon)
                title="VEGETABLES"
            }
            else if(item=="meat"){
                binding.image.setBackgroundResource(R.drawable.meat_icon)
                title="MEATS"
            }
            else if(item=="fruit"){
                binding.image.setBackgroundResource(R.drawable.fruit_icon)
                title="FRUITS"
            }
            else if(item=="fish"){
                binding.image.setBackgroundResource(R.drawable.fish_icon)
                title="FISH AND SEAFOODS"
            }
            else if(item=="grains"){
                binding.image.setBackgroundResource(R.drawable.grains_icon)
                title="GRAINS & BEANS & NUTS"
            }
            else if(item=="dairy"){
                binding.image.setBackgroundResource(R.drawable.dairies_icon)
                title="DAIRIES"
            }
            binding.ingredientHeader.text=title
        }

    }


}