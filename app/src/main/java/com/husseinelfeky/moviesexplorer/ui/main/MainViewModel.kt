package com.husseinelfeky.moviesexplorer.ui.main

import androidx.lifecycle.*
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.repository.MoviesDataSource
import com.husseinelfeky.moviesexplorer.utils.collectTo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesDataSource) : ViewModel() {

    private val _movieImages = MutableLiveData<Result<List<MovieImage>>>()
    val movieImages: LiveData<Result<List<MovieImage>>>
        get() = _movieImages

    // Used in data binding to show the progress bar.
    val imagesLoading: LiveData<Boolean>
        get() = Transformations.map(movieImages) {
            it.status == Result.Status.LOADING
        }

    fun getMovieImages(movieName: String) {
        viewModelScope.launch {
            moviesRepository.getMovieImages(movieName)
                .map { result ->
                    // Some movies do not have images returned from Flickr API.
                    if (result.status == Result.Status.SUCCESS && result.requireData().isEmpty()) {
                        Result.error("No images were found.", null)
                    } else {
                        result
                    }
                }
                .collectTo(_movieImages)
        }
    }
}
