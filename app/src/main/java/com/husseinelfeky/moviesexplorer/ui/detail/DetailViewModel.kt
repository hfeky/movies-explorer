package com.husseinelfeky.moviesexplorer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository

class DetailViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieName: String
) : ViewModel() {

    val movie = liveData { emit(moviesRepository.getMovieByName(movieName)) }
}
