package com.husseinelfeky.moviesexplorer.network

import com.husseinelfeky.moviesexplorer.model.FlickrPhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("services/rest/")
    suspend fun searchPhoto(
        @Query("method") method: String?,
        @Query("api_key") apiKey: String?,
        @Query("format") format: String?,
        @Query("nojsoncallback") noJson: Int?,
        @Query("text") searchText: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Response<FlickrPhotosResponse>
}
