package com.husseinelfeky.moviesexplorer.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository

class MasterViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesRepository.getAllMovies()
    }

    fun searchMoviesByName(searchQuery: String): LiveData<List<Movie>> {
        return moviesRepository.searchMoviesByName(searchQuery)
    }
}
