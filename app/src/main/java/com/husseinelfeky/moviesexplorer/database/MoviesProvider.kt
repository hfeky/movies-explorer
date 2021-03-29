package com.husseinelfeky.moviesexplorer.database

import android.app.Application
import com.husseinelfeky.moviesexplorer.model.MovieDto
import com.husseinelfeky.moviesexplorer.utils.readAssetFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MoviesProvider {

    /**
     * Populate the database with the movies data in movies.json file.
     */
    fun populateJsonData(application: Application, moshi: Moshi, database: LocalDb?) {
        val moviesFile = application.applicationContext.assets.readAssetFile("movies.json")

        val type = Types.newParameterizedType(List::class.java, MovieDto::class.java)
        val movies = moshi.adapter<List<MovieDto>>(type).fromJson(moviesFile)

        database?.moviesDao()?.apply {
            CoroutineScope(Dispatchers.IO).launch {
                movies?.forEach { movie ->
                    saveMovie(movie)
                }
            }
        }
    }
}
