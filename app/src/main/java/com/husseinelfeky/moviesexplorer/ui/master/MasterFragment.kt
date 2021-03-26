package com.husseinelfeky.moviesexplorer.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.husseinelfeky.moviesexplorer.databinding.FragmentMasterBinding
import com.husseinelfeky.moviesexplorer.ui.master.adapter.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MasterFragment : Fragment() {

    private val viewModel: MasterViewModel by viewModel()

    private lateinit var binding: FragmentMasterBinding

    private val moviesAdapter = MoviesAdapter {
        findNavController().navigate(
            MasterFragmentDirections.actionMasterFragmentToDetailFragment(
                it.movieName
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterBinding.inflate(inflater, container, false)
        initView()
        initObservers()
        return binding.root
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.rvMovies.adapter = moviesAdapter
    }

    private fun initObservers() {
        viewModel.movies.observe(viewLifecycleOwner) {
            Timber.d("Displayed movies count: ${it.size}")
            moviesAdapter.submitList(it)
        }
    }
}
