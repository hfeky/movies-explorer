package com.husseinelfeky.moviesexplorer

import android.app.Application
import com.husseinelfeky.moviesexplorer.koin.databaseModule
import com.husseinelfeky.moviesexplorer.koin.repositoriesModule
import com.husseinelfeky.moviesexplorer.koin.servicesModule
import com.husseinelfeky.moviesexplorer.koin.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviesExplorerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MoviesExplorerApp)
            logger(AndroidLogger())
            modules(
                databaseModule,
                repositoriesModule,
                viewModelsModule,
                servicesModule
            )
        }
    }
}
