package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import com.husseinelfeky.moviesexplorer.BuildConfig
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.model.YearWithMovies
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
) : MoviesDataSource {

    /**
     * Retrieve all cached movies.
     *
     * @return an observable list of all cached movies.
     */
    override fun getAllMovies(): LiveData<List<Movie>> {
        return moviesDao.getAllMovies()
    }

    /**
     * Search movies by name.
     *
     * Note that it is better to sort the data at the very end more than sorting it in the
     * SQLite query, as there will be less data thus less comparisons will be performed.
     *
     * @return an observable list of movies grouped by year as [YearWithMovies].
     */
    override fun searchMoviesByName(searchQuery: String): LiveData<List<YearWithMovies>> {
        return filterMoviesSearchResults(moviesDao.getMoviesByQuery(searchQuery))
    }

    /**
     * Get movie details by name.
     */
    override suspend fun getMovieByName(name: String): MovieDetails {
        return moviesDao.getMovieByName(name)
    }

    /**
     * Fetch at most [MovieImages.PAGE_COUNT] movie images using Flickr API.
     */
    override suspend fun getMovieImages(movieName: String): Flow<Result<List<MovieImage>>> {
        return flow {
            getResult("Failed to fetch movie images.") {
                flickrApiService.searchPhotos(
                    method = Flickr.METHOD_SEARCH,
                    apiKey = BuildConfig.FLICKR_API_KEY,
                    format = "json",
                    noJson = 1,
                    searchText = movieName
                )
            }
        }.mapResultTo {
            it?.photos?.photo
        }
    }
}
