package com.example.dinnerforyou.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dinnerforyou.databinding.ListBinding
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_select_ingredient.view.*


class IngredientListAdapter(
    val clickListener: (name: MutableList<String>) -> Unit,
    val ingredientList: List<String>
) :
    ListAdapter<String, IngredientListAdapter.IngredientListViewHolder>(
        IngredientListDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : IngredientListViewHolder = IngredientListViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, ingredientList)
    }

    class IngredientListViewHolder(val binding: ListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): IngredientListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListBinding.inflate(layoutInflater, parent, false)
                return IngredientListViewHolder(binding)
            }
        }

        fun bind(
            item: String,
            clickListener: (names: MutableList<String>) -> Unit,
            ingredientList: List<String>
        ) {
            var id = 0
            for (i in ingredientList) {
                id = ingredientList.indexOf(i)
                var chip = Chip(binding.root.context)
                chip.setText(i)
                chip.isClickable = true
                chip.isCheckable = true
                binding.chipGroup.addView(chip, id)
            }

            binding.fab.setOnClickListener {
                val checkedIds = binding.chipGroup.checkedChipIds
                var names = mutableListOf<String>()
                for (i in checkedIds) {
                    val chip:Chip= binding.chipGroup.findViewById(i)
                    val text= chip.text
                    names.add(text as String)
                }
                clickListener(names)
            }
        }

    }


}