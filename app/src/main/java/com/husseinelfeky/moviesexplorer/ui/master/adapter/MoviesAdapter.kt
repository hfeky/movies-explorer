package com.husseinelfeky.moviesexplorer.ui.master.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.R
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.Year

class MoviesAdapter(
    private val clickListener: (Movie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MoviesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie -> MovieViewHolder.create(parent, clickListener)
            R.layout.item_year -> YearViewHolder.create(parent)
            else -> throw IllegalArgumentException("An unknown view type was passed.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> (holder as MovieViewHolder).bind(getItem(position) as Movie)
            R.layout.item_year -> (holder as YearViewHolder).bind(getItem(position) as Year)
            else -> throw IllegalArgumentException("An unknown view type was passed.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Movie) {
            R.layout.item_movie
        } else {
            R.layout.item_year
        }
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.getUniqueIdentifier() == newItem.getUniqueIdentifier()
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
