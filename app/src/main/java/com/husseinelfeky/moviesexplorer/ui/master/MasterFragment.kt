package com.husseinelfeky.moviesexplorer.ui.master

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.husseinelfeky.moviesexplorer.R
import com.husseinelfeky.moviesexplorer.databinding.FragmentMasterBinding
import com.husseinelfeky.moviesexplorer.ui.master.adapter.MoviesAdapter
import com.husseinelfeky.moviesexplorer.utils.observeChanges
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
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

        setHasOptionsMenu(true)
    }

    private fun initObservers() {
        // Display all movies initially.
        viewModel.getAllMovies().observeChanges(
            viewLifecycleOwner,
            moviesAdapter
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_master, menu)

        val item = menu.findItem(R.id.action_search).apply {
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    binding.motionLayout.transitionToEnd()
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    binding.motionLayout.transitionToStart()

                    // Display all movies back.
                    viewModel.getAllMovies().observeChanges(
                        viewLifecycleOwner,
                        moviesAdapter
                    )

                    return true
                }
            })
        }

        (item.actionView as SearchView).apply {
            // Set maxWidth to max value to expand the SearchView width to the whole toolbar.
            maxWidth = Integer.MAX_VALUE

            queryHint = getString(R.string.hint_search_movie)

            // Do a debounce operation to reduce the overhead of searching.
            callbackFlow<String> {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.searchMoviesByName(query).observeChanges(
                            viewLifecycleOwner,
                            moviesAdapter
                        )
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        offer(newText)
                        return true
                    }
                })
                awaitClose { setOnQueryTextListener(null) }
            }.debounce(300)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .onEach {
                    // Check if the search view is visible first, so that the callback is not fired
                    // if the user has already closed the search view.
                    if (item.isActionViewExpanded) {
                        if (it.isNotEmpty()) {
                            viewModel.searchMoviesByName(it).observeChanges(
                                viewLifecycleOwner,
                                moviesAdapter
                            )
                        } else {
                            // If there is no search query, display all movies back.
                            viewModel.getAllMovies().observeChanges(
                                viewLifecycleOwner,
                                moviesAdapter
                            )
                        }
                    }
                }
                .launchIn(lifecycleScope)
        }
    }
}
