package com.husseinelfeky.moviesexplorer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository

class DetailViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieName: String
) : ViewModel() {

    // Hold the movie details inside a LiveData in the ViewModel to survive
    // configuration changes, yet do not make the repository return a LiveData
    // of the movie details to remove the unnecessary overhead that gets created
    // as when a DAO query returns a LiveData, triggers are actually created in
    // the SQLite database, which is unnecessary in this case, as the data will
    // never be updated.
    val movie = liveData { emit(moviesRepository.getMovieByName(movieName)) }
}
