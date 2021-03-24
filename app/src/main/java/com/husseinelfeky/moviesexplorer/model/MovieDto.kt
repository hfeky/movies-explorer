package com.husseinelfeky.moviesexplorer.model

data class MovieDto(
    val title: String,
    val rating: Int,
    val year: Int,
    val genres: List<String>,
    val cast: List<String>
)
