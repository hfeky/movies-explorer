package com.husseinelfeky.moviesexplorer.database.crossref

import androidx.room.Entity

@Entity(primaryKeys = ["movieName", "genreName"])
data class MovieGenreCrossRef(
    val movieName: String,
    val genreName: String
)
