package com.husseinelfeky.moviesexplorer.database.crossref

import androidx.room.Entity

@Entity(primaryKeys = ["movieName", "castMemberName"])
data class MovieCastCrossRef(
    val movieName: String,
    val castMemberName: String
)
