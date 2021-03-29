package com.husseinelfeky.moviesexplorer.network

import com.husseinelfeky.moviesexplorer.model.FlickrPhotosResponse
import com.husseinelfeky.moviesexplorer.utils.MovieImages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("services/rest/")
    suspend fun searchPhotos(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJson: Int,
        @Query("text") searchText: String,
        @Query("page") page: Int = MovieImages.INITIAL_PAGE,
        @Query("per_page") perPage: Int = MovieImages.PAGE_COUNT
    ): Response<FlickrPhotosResponse>
}
