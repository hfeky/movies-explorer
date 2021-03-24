package com.husseinelfeky.moviesexplorer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.husseinelfeky.moviesexplorer.database.converter.DateConverter
import com.husseinelfeky.moviesexplorer.database.crossref.MovieCastCrossRef
import com.husseinelfeky.moviesexplorer.database.crossref.MovieGenreCrossRef
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.database.entity.Genre
import com.husseinelfeky.moviesexplorer.database.entity.Movie

@Database(
    entities = [
        Movie::class,
        Genre::class,
        CastMember::class,
        MovieGenreCrossRef::class,
        MovieCastCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class LocalDb : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}
