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
import com.example.dinnerforyou.ViewModelFactories.SignUpViewModelFactory
import com.example.dinnerforyou.SignUpViewModel
import com.example.dinnerforyou.databinding.FragmentSignUpPageBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class SignUpPage : Fragment() {
    private var _binding: FragmentSignUpPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpPageBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = SignUpViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SignUpViewModel::class.java)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding.createAccountButton.setOnClickListener {
            if (viewModel.isBlanksFilled()) {
                viewModel.isEmailExist()

            } else {
                val snackbar =
                    Snackbar.make(view, "You must fill all the blanks!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isEmailExist.collect { isExist ->
                if (isExist) {
                    val snackbar = Snackbar.make(
                        view,
                        "This email address is already used by another account!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                } else {
                    viewModel.createAccount()
                    view.findNavController().navigate(R.id.action_signUpPage_to_selectIngredient)
                }
            }
        }
    }
}