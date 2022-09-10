package com.husseinelfeky.moviesexplorer.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.Year
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem

class MasterViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesRepository.getAllMovies()
    }

    fun searchMoviesByName(searchQuery: String): LiveData<List<DifferentiableItem>> {
        // Map the list to be displayed as two view types in the recycler view.
        return moviesRepository.searchMoviesByName(searchQuery).map { list ->
            mutableListOf<DifferentiableItem>().apply {
                list.forEach { group ->
                    add(Year(group.year))
                    addAll(group.movies)
                }
            }
        }
    }
}
