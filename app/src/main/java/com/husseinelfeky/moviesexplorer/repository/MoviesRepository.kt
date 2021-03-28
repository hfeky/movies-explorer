package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import com.husseinelfeky.moviesexplorer.BuildConfig
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.network.FlickrApiService
import com.husseinelfeky.moviesexplorer.utils.Flickr
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

    suspend fun getMovieByName(name: String): MovieDetails {
        return moviesDao.getMovieByName(name)
    }

    suspend fun getMovieImages(
        movieName: String,
        page: Int = 1,
        perPage: Int = 5
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
