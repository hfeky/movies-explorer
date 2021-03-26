package com.husseinelfeky.moviesexplorer.ui.master

import androidx.lifecycle.ViewModel
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository

class MasterViewModel(moviesRepository: MoviesRepository) : ViewModel() {

    val movies = moviesRepository.getAllMovies()
}
