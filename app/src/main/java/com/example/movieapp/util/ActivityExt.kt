package com.example.movieapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.R

fun AppCompatActivity.requireNavController(): NavController =
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController