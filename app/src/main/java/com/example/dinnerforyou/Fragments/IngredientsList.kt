package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dinnerforyou.Adapters.IngredientListAdapter
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.R
import com.example.dinnerforyou.ViewModelFactories.IngredientListViewModelFactory
import com.example.dinnerforyou.IngredientListViewModel
import com.example.dinnerforyou.databinding.FragmentIngredientsListBinding


class IngredientsList : Fragment() {

    private var _binding: FragmentIngredientsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IngredientListViewModel

    private var _list: MutableList<String> = mutableListOf()
    val list: List<String>
        get() = _list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsListBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).ingredientDao

        val type = IngredientsListArgs.fromBundle(requireArguments()).type

        val viewModelFactory = IngredientListViewModelFactory(dao, type)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(IngredientListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.image.setBackgroundResource(viewModel.setImage(type))
        binding.toolbar.title = viewModel.setTitle(type)

        binding.ingredientList.setNestedScrollingEnabled(true)
        viewModel.list.observe(viewLifecycleOwner, Observer {
            val adapter = IngredientListAdapter({ names ->
                viewModel.add(names)
                view.findNavController().navigate(R.id.action_ingredientsList_to_selectIngredient)
            }, it)
            binding.ingredientList.adapter = adapter
            viewModel.elist.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        })

        return view
    }


}
