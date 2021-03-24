package com.husseinelfeky.moviesexplorer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.husseinelfeky.moviesexplorer.database.entity.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<MutableList<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(vararg movies: MovieEntity)
}
