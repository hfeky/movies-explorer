package com.husseinelfeky.moviesexplorer.koin

import com.husseinelfeky.moviesexplorer.repository.MoviesDataSource
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<MoviesDataSource> {
        MoviesRepository(
            moviesDao = get(),
            flickrApiService = get()
        )
    }
}
