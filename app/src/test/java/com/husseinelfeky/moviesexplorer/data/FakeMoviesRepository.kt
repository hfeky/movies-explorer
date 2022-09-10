package com.husseinelfeky.moviesexplorer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.model.YearWithMovies
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.utils.FakeMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMoviesRepository(private val moviesList: List<Movie>) : MoviesRepository {

    private var shouldReturnError = false

    fun setReturnError(shouldReturnError: Boolean) {
        this.shouldReturnError = shouldReturnError
    }

    override fun getAllMovies(): LiveData<List<Movie>> {
        return MutableLiveData(moviesList)
    }

    override fun searchMoviesByName(searchQuery: String): LiveData<List<YearWithMovies>> {
        val searchResults = MutableLiveData(
            moviesList.filter {
                it.movieName.contains(searchQuery, ignoreCase = true)
            }
        )
        return filterMoviesSearchResults(searchResults)
    }

    /**
     * For simplicity, we will always assume that the database will always contain
     * a movie with the passed parameter name.
     *
     * @return the fake movie details with the name set as the passed parameter name.
     */
    override suspend fun getMovieByName(name: String): MovieDetails {
        return FakeMovies.getMovieDetails().copy(movieName = name)
    }

    /**
     * For simplicity, we will always assume that all movies will return an empty list of images.
     *
     * @return an error if [shouldReturnError] is true, an empty list otherwise.
     */
    override suspend fun getMovieImages(movieName: String): Flow<Result<List<MovieImage>>> {
        return flow {
            emit(
                if (shouldReturnError) {
                    Result.error("Failed to fetch movie images.", null)
                } else {
                    Result.success(emptyList())
                }
            )
        }
    }
}
