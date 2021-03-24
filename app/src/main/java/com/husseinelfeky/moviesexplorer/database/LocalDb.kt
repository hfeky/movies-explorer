package com.husseinelfeky.moviesexplorer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.husseinelfeky.moviesexplorer.database.converter.DateConverter
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class LocalDb : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}
