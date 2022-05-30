package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.dinnerforyou.Adapters.AddedItemsAdapter
import com.example.dinnerforyou.Adapters.IngredientTypesAdapter
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.ViewModelFactories.IngredientViewModelFactory
import com.example.dinnerforyou.IngredientViewModel
import com.example.dinnerforyou.R
import com.example.dinnerforyou.databinding.FragmentSelectIngredientBinding
import kotlinx.coroutines.flow.collect

class SelectIngredient : Fragment() {

    private var _binding: com.example.dinnerforyou.databinding.FragmentSelectIngredientBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IngredientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectIngredientBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).ingredientDao

        val viewModelFactory = IngredientViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(IngredientViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.ingredientHeader.setNestedScrollingEnabled(false)

        if (!addedItems._items.isNullOrEmpty()) {

            val adapter = context?.let {
                AddedItemsAdapter(
                    requireContext(), addedItems._items
                )
            }
            binding.addedItemsList.adapter = adapter
        }
        viewModel.setTypesList()

        binding.findRecipesButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_selectIngredient_to_recipeCombinationFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.typesChannel.collect {
                if (it) {
                    val adapter = IngredientTypesAdapter { type ->
                        viewModel.onClicked(type)
                    }

                    binding.ingredientHeader.adapter = adapter
                    viewModel.typesList.observe(
                        viewLifecycleOwner, Observer {
                            it?.let {
                                adapter.submitList(it)
                            }
                        }
                    )

                }
            }

        }

        viewModel.selectedType.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action =
                    SelectIngredientDirections.selectToList(it)
                view.findNavController().navigate(action)
            }
        })
    }

}

object addedItems {
    var _items: MutableList<String> = mutableListOf()
}
