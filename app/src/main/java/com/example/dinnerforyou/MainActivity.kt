package com.example.dinnerforyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dinnerforyou.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.signInPage) {
                binding.bottomNavigationView.visibility = View.INVISIBLE
            } else if (destination.id == R.id.signUpPage) {
                binding.bottomNavigationView.visibility = View.INVISIBLE
            } else if (destination.id == R.id.resetPasswordPage) {
                binding.bottomNavigationView.visibility = View.INVISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}