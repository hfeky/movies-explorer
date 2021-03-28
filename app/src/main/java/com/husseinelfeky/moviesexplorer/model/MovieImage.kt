package com.husseinelfeky.moviesexplorer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrPhotosResponse(
    val photos: MovieImagesResponse
)

data class MovieImagesResponse(
    val photo: List<MovieImage>
)

data class MovieImage(
    val title: String,
    val id: String,
    val secret: String,
    val farm: String,
    val server: String
) {

    val url: String
        get() = "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"
}
