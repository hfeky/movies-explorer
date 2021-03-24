package com.husseinelfeky.moviesexplorer.database.relation

import androidx.room.Junction
import androidx.room.Relation
import com.husseinelfeky.moviesexplorer.database.crossref.MovieCastCrossRef
import com.husseinelfeky.moviesexplorer.database.crossref.MovieGenreCrossRef
import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.database.entity.Genre

data class MovieDetails(
    val movieName: String,
    val rating: Int,
    val year: Int,
    @Relation(
        parentColumn = "movieName",
        entityColumn = "genreName",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<Genre>,
    @Relation(
        parentColumn = "movieName",
        entityColumn = "castMemberName",
        associateBy = Junction(MovieCastCrossRef::class)
    )
    val cast: List<CastMember>
)
