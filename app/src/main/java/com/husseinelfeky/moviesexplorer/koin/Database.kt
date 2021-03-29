package com.husseinelfeky.moviesexplorer.koin

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.husseinelfeky.moviesexplorer.database.LocalDb
import com.husseinelfeky.moviesexplorer.database.MoviesProvider
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
            // Populate the database with the movies data in movies.json file.
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    MoviesProvider.populateJsonData(androidApplication(), get(), database)
                }
            })
            .build()

        database
    }
    single { get<LocalDb>().moviesDao() }
}
