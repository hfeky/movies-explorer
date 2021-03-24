package com.husseinelfeky.moviesexplorer.koin

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.husseinelfeky.moviesexplorer.database.LocalDb
import com.husseinelfeky.moviesexplorer.model.MovieDto
import com.husseinelfeky.moviesexplorer.utils.readAssetFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        var database: LocalDb? = null

        database = Room.databaseBuilder(
            androidContext(),
            LocalDb::class.java,
            LocalDb::class.java.simpleName
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    val moviesFile =
                        androidApplication().applicationContext.assets.readAssetFile(
                            "movies.json"
                        )

                    val moshi: Moshi = get()
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
            })
            .build()

        database
    }
    single { get<LocalDb>().moviesDao() }
}
