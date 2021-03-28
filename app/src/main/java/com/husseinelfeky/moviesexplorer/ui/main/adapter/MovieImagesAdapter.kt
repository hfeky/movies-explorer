package com.husseinelfeky.moviesexplorer.ui.main.adapter

import android.view.ViewGroup
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.smarteist.autoimageslider.SliderViewAdapter

class MovieImagesAdapter : SliderViewAdapter<MovieImageViewHolder>() {

    private var movieImages: List<MovieImage> = arrayListOf()

    override fun getCount(): Int = movieImages.size

    override fun onCreateViewHolder(parent: ViewGroup): MovieImageViewHolder {
        return MovieImageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        holder.bind(movieImages[position])
    }

    fun setData(movieImages: List<MovieImage>) {
        this.movieImages = movieImages
        notifyDataSetChanged()
    }
}
