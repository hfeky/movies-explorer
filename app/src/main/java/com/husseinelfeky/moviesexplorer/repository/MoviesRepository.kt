package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.husseinelfeky.moviesexplorer.BuildConfig
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.network.FlickrApiService
import com.husseinelfeky.moviesexplorer.utils.Flickr
import com.husseinelfeky.moviesexplorer.utils.MovieImages
import com.husseinelfeky.moviesexplorer.utils.getResult
import com.husseinelfeky.moviesexplorer.utils.mapResultTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepository(
    private val moviesDao: MoviesDao,
    private val flickrApiService: FlickrApiService
) {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesDao.getAllMovies()
    }

    fun searchMoviesByName(searchQuery: String): LiveData<List<Movie>> {
        // The results are already filtered from the DAO.
        // We only need to group them and get top 5 movies of each year.
        return moviesDao.getMoviesByQuery(searchQuery).map { movies ->
            // Group (categorize) the results by year.
            movies.groupBy { it.year }
                // Transform the map to a list.
                .flatMap { year ->
                    // Sort the results first with rating in descending order,
                    // then by movie name in ascending order.
                    year.value.sortedWith(compareBy({ -it.rating }, { it.movieName }))
                        // Then take only the top 5 movies of each year.
                        .take(5)
                }
        }
    }

    suspend fun getMovieByName(name: String): MovieDetails {
        return moviesDao.getMovieByName(name)
    }

    suspend fun getMovieImages(
        movieName: String,
        page: Int = MovieImages.INITIAL_PAGE,
        perPage: Int = MovieImages.PAGE_COUNT
    ): Flow<Result<List<MovieImage>>> {
        return flow {
            getResult("Failed to fetch movie images.") {
                flickrApiService.searchPhoto(
                    method = Flickr.METHOD_SEARCH,
                    apiKey = BuildConfig.FLICKR_API_KEY,
                    format = "json",
                    noJson = 1,
                    searchText = movieName,
                    page = page,
                    perPage = perPage
                )
            }
        }.mapResultTo {
            it?.photos?.photo
        }
    }
}
