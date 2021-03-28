package com.husseinelfeky.moviesexplorer.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.husseinelfeky.moviesexplorer.databinding.ItemMovieImageBinding
import com.husseinelfeky.moviesexplorer.model.MovieImage
import com.smarteist.autoimageslider.SliderViewAdapter

class MovieImageViewHolder(
    private val binding: ItemMovieImageBinding
) : SliderViewAdapter.ViewHolder(binding.root) {

    fun bind(movieImage: MovieImage) {
        binding.movieImage = movieImage
        binding.executePendingBindings()
    }

    companion object {
        fun create(container: ViewGroup): MovieImageViewHolder {
            val binding = ItemMovieImageBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return MovieImageViewHolder(binding)
        }
    }
}
