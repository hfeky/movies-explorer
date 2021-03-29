package com.husseinelfeky.moviesexplorer.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.husseinelfeky.moviesexplorer.R
import com.husseinelfeky.moviesexplorer.databinding.ActivityMainBinding
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.ui.main.adapter.MovieImagesAdapter
import com.husseinelfeky.moviesexplorer.utils.MovieImages
import com.husseinelfeky.moviesexplorer.utils.showSnackbar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private var isFirstLaunch = true

    private val movieImagesAdapter: MovieImagesAdapter by lazy { MovieImagesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initNavController()
        initObservers()
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
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
                    binding.apply {
                        // Collapse and disable the CollapsingToolbarLayout.
                        appBar.setExpanded(false, !isFirstLaunch)
                        appBar.isActivated = false

                        // Hide movie images views.
                        svImages.visibility = View.GONE
                        layoutErrorState.visibility = View.GONE
                        pbImages.visibility = View.GONE
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

    private fun initObservers() {
        // Show the appropriate views according to the result status.
        viewModel.movieImages.observe(this) { result ->
            binding.apply {
                when (result.status) {
                    Result.Status.LOADING -> {
                        svImages.visibility = View.GONE
                        layoutErrorState.visibility = View.GONE
                        pbImages.visibility = View.VISIBLE
                    }
                    Result.Status.SUCCESS -> {
                        layoutErrorState.visibility = View.GONE
                        svImages.visibility = View.VISIBLE
                        if (svImages.sliderAdapter == null) {
                            // Initialize the images slider adapter.
                            svImages.setIndicatorAnimation(IndicatorAnimationType.DROP)
                            svImages.setOffscreenPageLimit(MovieImages.PAGE_COUNT)
                            svImages.setSliderAdapter(movieImagesAdapter)
                        }

                        movieImagesAdapter.setData(result.requireData())
                        svImages.setInfiniteAdapterEnabled(true)
                    }
                    Result.Status.ERROR -> {
                        pbImages.visibility = View.GONE
                        svImages.visibility = View.GONE
                        layoutErrorState.visibility = View.VISIBLE
                        ivError.contentDescription = result.message

                        // Show the error message.
                        showSnackbar(result.requireMessage())
                    }
                }
            }
        }
    }
}
