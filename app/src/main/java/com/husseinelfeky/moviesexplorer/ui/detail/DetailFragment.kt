package com.husseinelfeky.moviesexplorer.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.husseinelfeky.moviesexplorer.databinding.FragmentDetailBinding
import com.husseinelfeky.moviesexplorer.ui.detail.adapter.CastAdapter
import com.husseinelfeky.moviesexplorer.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(args.movieName)
    }

    private lateinit var binding: FragmentDetailBinding

    private val castAdapter = CastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initView()
        initObservers()
        initData()
        return binding.root
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvCast.adapter = castAdapter
    }

    private fun initObservers() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.cgGenres.removeAllViews()
            movie.genres.forEach { genre ->
                binding.cgGenres.addView(
                    Chip(requireContext()).apply {
                        text = genre.genreName
                    }
                )
            }

            castAdapter.submitList(movie.cast)
        }
    }

    private fun initData() {
        mainViewModel.getMovieImages(args.movieName)
    }
}
