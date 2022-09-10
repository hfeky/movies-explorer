package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.model.YearWithMovies
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getAllMovies(): LiveData<List<Movie>>

    fun searchMoviesByName(searchQuery: String): LiveData<List<YearWithMovies>>

    suspend fun getMovieByName(name: String): MovieDetails

    suspend fun getMovieImages(movieName: String): Flow<Result<List<MovieImage>>>

    fun filterMoviesSearchResults(searchResults: LiveData<List<Movie>>): LiveData<List<YearWithMovies>> {
        // The results are already filtered from the DAO.
        // We only need to group them, get the top 5 movies of each year and sort them.
        return searchResults.map { movies ->
            // Group (categorize) the results by year.
            movies.groupBy { it.year }
                // Transform the map to a list of [YearWithMovies].
                .map { year ->
                    YearWithMovies(
                        year.key,
                        // Sort the results first with rating in descending order,
                        // then by movie name in ascending order.
                        year.value.sortedWith(compareBy({ -it.rating }, { it.movieName }))
                            // Then take only the top 5 movies of each year.
                            .take(5)
                    )
                }
                // Sort the groups by year in descending order.
                .sortedByDescending(YearWithMovies::year)
        }
    }
}
