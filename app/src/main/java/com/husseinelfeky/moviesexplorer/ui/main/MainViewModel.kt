package com.husseinelfeky.moviesexplorer.ui.main

import androidx.lifecycle.*
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.utils.collectTo
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieImages = MutableLiveData<Result<List<MovieImage>>>()
    val movieImages: LiveData<Result<List<MovieImage>>>
        get() = _movieImages

    val imagesLoading: LiveData<Boolean>
        get() = Transformations.map(movieImages) {
            it.status == Result.Status.LOADING
        }

    fun getMovieImages(movieName: String) {
        viewModelScope.launch {
            moviesRepository.getMovieImages(movieName).collectTo(_movieImages)
        }
    }
}
