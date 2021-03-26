package com.husseinelfeky.moviesexplorer.ui.master.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItemDiffUtil

class MoviesAdapter(
    private val clickListener: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(DifferentiableItemDiffUtil.getInstance()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, clickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
