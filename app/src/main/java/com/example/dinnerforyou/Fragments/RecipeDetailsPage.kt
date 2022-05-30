package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.R
import com.example.dinnerforyou.RecipeDetailsViewModel
import com.example.dinnerforyou.ViewModelFactories.RecipeDetailsViewModelFactory
import com.example.dinnerforyou.databinding.FragmentRecipeDetailsPageBinding
import com.example.dinnerforyou.selectedRecipe
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class RecipeDetailsPage : Fragment() {

    private var _binding: FragmentRecipeDetailsPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecipeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailsPageBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = RecipeDetailsViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(RecipeDetailsViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.ingredients.observe(viewLifecycleOwner, Observer {
            binding.ingredients.text = viewModel.setIngredientList()
        })
        viewModel.description.observe(viewLifecycleOwner, Observer {
            binding.description.text = viewModel.description.value
        })

        binding.toolbar.title = selectedRecipe.recipeName.toUpperCase()

        binding.extendedFab.setOnClickListener {
            viewModel.checkFavList()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isRecipeExistChannel.collect {
                if (it) {
                    Snackbar.make(view, "It has already in the favorite list!", Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    viewModel.addFavList()
                    Snackbar.make(view, "Added!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}