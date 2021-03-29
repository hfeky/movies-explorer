package com.husseinelfeky.moviesexplorer.model

import com.husseinelfeky.moviesexplorer.database.entity.Movie

data class MovieDto(
    val title: String,
    val rating: Int,
    val year: Int,
    val genres: List<String>,
    val cast: List<String>
) {

    fun toDomainModel(): Movie {
        return Movie(title, rating, year)
    }
}
