package com.example.dinnerforyou.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dinnerforyou.Database.AppDatabase
import com.example.dinnerforyou.ViewModelFactories.SignInViewModelFactory
import com.example.dinnerforyou.SignInViewModel
import com.example.dinnerforyou.databinding.FragmentResetPasswordPageBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class ResetPasswordPage : Fragment() {
    private var _binding: FragmentResetPasswordPageBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordPageBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao

        val viewModelFactory = SignInViewModelFactory(dao)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SignInViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.resetButton.setOnClickListener {
            if (viewModel.isBlanksFilled()) {
                viewModel.resetPassword()
            } else {
                val snackbar =
                    Snackbar.make(view, "You must fill email and password!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isUserExist.collect {
                if (it) {
                    viewModel.resetPassword()
                } else {
                    val snackbar = Snackbar.make(view, "Email is wrong!", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }

            viewModel.isUpdateSuccesfull.collect {
                if(it){
                    val snackbar =
                        Snackbar.make(view, "Password changed successfully!", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }

            }
        }
    }
}