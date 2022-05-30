package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.R
import com.example.dinnerforyou.ViewModelFactories.SignInViewModelFactory
import com.example.dinnerforyou.SignInViewModel

import com.example.dinnerforyou.databinding.FragmentSignInPageBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class SignInPage : Fragment() {
    private var _binding: FragmentSignInPageBinding? =null
    private val binding get()=_binding!!
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentSignInPageBinding.inflate(inflater,container,false)
        val view= binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = SignInViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(SignInViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.signInButton.setOnClickListener {
            if(viewModel.isBlanksFilled()){
               viewModel.checkAccountValidity()
            }
            else{
                val snackbar= Snackbar.make(view,"You must fill email and password!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }

        binding.resetPasswordButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_signInPage_to_resetPasswordPage)
        }

        binding.createAccountButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_signInPage_to_signUpPage)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUserName.collect {
                if(it){
                    userInfo.userName=viewModel.userName
                    userInfo.userEmail=viewModel.email
                    view.findNavController().navigate(R.id.action_signInPage_to_selectIngredient)

                }
                else{
                    val snackbar= Snackbar.make(view,"Account info is wrong!", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }
    }
}