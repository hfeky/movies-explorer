package com.husseinelfeky.moviesexplorer.koin

import com.husseinelfeky.moviesexplorer.ui.detail.DetailViewModel
import com.husseinelfeky.moviesexplorer.ui.master.MasterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MasterViewModel(
            moviesRepository = get()
        )
    }
    viewModel { (movieName: String) ->
        DetailViewModel(
            moviesRepository = get(),
            movieName = movieName
        )
    }
}
