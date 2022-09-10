package com.husseinelfeky.moviesexplorer.koin

import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.repository.MoviesRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    factory<MoviesRepository> {
        MoviesRepositoryImpl(
            moviesDao = get(),
            flickrApiService = get()
        )
    }
}
