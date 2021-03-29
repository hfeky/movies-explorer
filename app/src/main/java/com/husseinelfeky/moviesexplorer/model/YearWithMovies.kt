package com.husseinelfeky.moviesexplorer.model

import com.husseinelfeky.moviesexplorer.database.entity.Movie

data class YearWithMovies(
    val year: Int,
    val movies: List<Movie>
)
