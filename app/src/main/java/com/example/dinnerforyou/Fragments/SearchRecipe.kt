package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.dinnerforyou.Adapters.RecipeAdapter
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.ProfileViewModel
import com.example.dinnerforyou.R
import com.example.dinnerforyou.SearchViewModel
import com.example.dinnerforyou.ViewModelFactories.ProfileViewModelFactory
import com.example.dinnerforyou.ViewModelFactories.SearchViewModelFactory
import com.example.dinnerforyou.databinding.FragmentProfileBinding
import com.example.dinnerforyou.databinding.FragmentSearchRecipeBinding
import kotlinx.coroutines.flow.collect

class SearchRecipe : Fragment() {
    private var _binding: FragmentSearchRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = SearchViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SearchViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.onNavigate.observe(viewLifecycleOwner, Observer {
            if(it==true){
                val action =
                    SearchRecipeDirections.actionSearchRecipeToRecipeDetailsPage()
                view.findNavController().navigate(action)
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isRecipeExistChannel.collect {
                if(it){
                    val adapter=RecipeAdapter({id ->
                        viewModel.onNavigate(id)
                    })
                    binding.matchingRecipes.adapter=adapter
                    viewModel.foundRecipes.observe(viewLifecycleOwner, Observer {
                        it?.let {
                            adapter.submitList(it)
                        }
                    })
                }
            }
        }
    }
}
