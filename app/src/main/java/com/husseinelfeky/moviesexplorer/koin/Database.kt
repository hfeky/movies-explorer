package com.husseinelfeky.moviesexplorer.koin

import androidx.room.Room
import com.husseinelfeky.moviesexplorer.database.LocalDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDb::class.java,
            LocalDb::class.java.simpleName
        ).fallbackToDestructiveMigration()
            .build()
    }
    single { get<LocalDb>().moviesDao() }
}
