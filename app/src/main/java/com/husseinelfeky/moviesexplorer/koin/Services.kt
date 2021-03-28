package com.husseinelfeky.moviesexplorer.koin

import com.husseinelfeky.moviesexplorer.network.FlickrApiService
import com.husseinelfeky.moviesexplorer.utils.Flickr
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val servicesModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Flickr.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(FlickrApiService::class.java)
    }
}
