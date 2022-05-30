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
import com.example.dinnerforyou.Adapters.RecipeAdapter
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.FavRecipeViewModel
import com.example.dinnerforyou.R
import com.example.dinnerforyou.ViewModelFactories.FavRecipeViewModelFactory
import com.example.dinnerforyou.databinding.FragmentFavoriteRecipesBinding
import kotlinx.coroutines.flow.collect


class FavoriteRecipesFragment : Fragment() {
    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFav: FavRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = FavRecipeViewModelFactory(dao)
        viewModelFav = ViewModelProvider(
            this, viewModelFactory
        ).get(FavRecipeViewModel::class.java)

        binding.viewModel = viewModelFav
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModelFav.findRecipes()
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModelFav.favRecipeChannel.collect {
                binding.message.text=""
                if (it) {
                    val adapter = RecipeAdapter({ id ->
                        viewModelFav.onNavigate(id)
                    })
                    binding.recipeList.adapter = adapter
                    viewModelFav.favorites.observe(viewLifecycleOwner, Observer {
                        it?.let {
                            adapter.submitList(it)
                        }
                    })
                }
                else{
                    binding.message.text="NO FAVORITE ITEMS ADDED!"
                }
            }
        }

        viewModelFav.onNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                view.findNavController().navigate(R.id.action_recipesFragment_to_recipeDetailsPage)
            }
        })
    }
}