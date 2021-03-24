package com.husseinelfeky.moviesexplorer.repository

import androidx.lifecycle.LiveData
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie

class MoviesRepository(private val moviesDao: MoviesDao) {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesDao.getAllMovies()
    }
}
