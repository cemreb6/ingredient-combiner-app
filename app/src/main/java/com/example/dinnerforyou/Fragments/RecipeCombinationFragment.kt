package com.example.dinnerforyou.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.example.dinnerforyou.R
import com.example.dinnerforyou.RecipeCombinationViewModel
import com.example.dinnerforyou.ViewModelFactories.RecipeCombinationViewModelFactory
import com.example.dinnerforyou.databinding.FragmentRecipeCombinationBinding
import kotlinx.coroutines.flow.collect

class RecipeCombinationFragment : Fragment() {
    private var _binding: FragmentRecipeCombinationBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: RecipeCombinationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRecipeCombinationBinding.inflate(inflater,container,false)
        val view= binding.root

        val application= requireNotNull(this.activity).application
        val dao=AppDatabase.getInstance((application)).ingredientDao

        val viewModelFactory=RecipeCombinationViewModelFactory(dao)
        viewModel=ViewModelProvider(this,viewModelFactory).get(RecipeCombinationViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel.findRecipes()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
           viewModel.recipeFoundedChannel.collect {
               if(it){
                   Log.e(TAG, viewModel.matchingRecipes.toString() )
                   val adapter = RecipeAdapter({ id ->
                       viewModel.onNavigate(id)
                   })
                   binding.combinationList.adapter = adapter
                   viewModel.matchingRecipes.observe(viewLifecycleOwner,Observer{
                      it.let {
                          adapter.submitList(it)
                      }
                   })

               }
               else{
                   binding.message.text="NO MATCHING RECIPE!"
               }
           }
        }

        viewModel.onNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                view.findNavController().navigate(R.id.action_recipeCombinationFragment_to_recipeDetailsPage)
            }
        })

    }
}