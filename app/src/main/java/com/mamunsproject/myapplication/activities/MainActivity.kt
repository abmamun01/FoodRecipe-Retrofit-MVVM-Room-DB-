package com.mamunsproject.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.mamunsproject.myapplication.R
import com.mamunsproject.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding = ResultProfileBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController=Navigation.findNavController(this,R.id.host_fragment)

        // BottomNavigation e click korle direct j fragment r name menu create korechi sekhane cole jabe
        binding.bottomNavigation.setupWithNavController(navController)


    }
}