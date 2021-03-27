package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails

class MoviesRepository(private val moviesDao: MoviesDao) {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesDao.getAllMovies()
    }

    suspend fun getMovieByName(name: String): MovieDetails {
        return moviesDao.getMovieByName(name)
    }
}
