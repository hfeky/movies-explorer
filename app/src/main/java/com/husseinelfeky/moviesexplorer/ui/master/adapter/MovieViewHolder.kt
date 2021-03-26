package com.husseinelfeky.moviesexplorer.ui.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie
        binding.handler = this
        binding.executePendingBindings()
    }

    fun click(movie: Movie) {
        clickListener.invoke(movie)
    }

    companion object {
        fun create(container: ViewGroup, clickListener: (Movie) -> Unit): MovieViewHolder {
            val binding = ItemMovieBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return MovieViewHolder(binding, clickListener)
        }
    }
}
