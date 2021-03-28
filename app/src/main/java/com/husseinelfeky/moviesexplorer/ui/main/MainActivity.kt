package com.husseinelfeky.moviesexplorer.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.husseinelfeky.moviesexplorer.R
import com.husseinelfeky.moviesexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initNavController()
    }

    private fun initNavController() {
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(
            binding.collapsingToolbar,
            binding.toolbar,
            navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.masterFragment -> {
                    // Collapse and disable the CollapsingToolbarLayout.
                    binding.appBar.apply {
                        setExpanded(false, !isFirstLaunch)
                        isActivated = false
                    }
                    isFirstLaunch = false
                }
                R.id.detailFragment -> {
                    // Expand and enable the CollapsingToolbarLayout.
                    binding.appBar.apply {
                        setExpanded(true, true)
                        isActivated = true
                    }
                }
            }
        }
    }
}
