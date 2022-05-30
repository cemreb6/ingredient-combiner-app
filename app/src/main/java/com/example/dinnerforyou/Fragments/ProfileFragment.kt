package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.ProfileViewModel
import com.example.dinnerforyou.R
import com.example.dinnerforyou.ViewModelFactories.ProfileViewModelFactory
import com.example.dinnerforyou.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = ProfileViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(ProfileViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.signOutButton.setOnClickListener{
            addedItems._items.clear()
            view.findNavController().navigate(R.id.action_profileFragment_to_signInPage)
        }
        binding.favoriteRecipesButton.setOnClickListener {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToFavoriteRecipesFragment()
            view.findNavController().navigate(action)
        }
        return view
    }

}

object userInfo{
    var userName=""
    var userEmail=""
}