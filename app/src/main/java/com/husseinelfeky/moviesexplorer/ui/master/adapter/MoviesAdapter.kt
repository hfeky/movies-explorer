package com.husseinelfeky.moviesexplorer.ui.master.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.R
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.Year
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItemDiffUtil

class MoviesAdapter(
    private val clickListener: (Movie) -> Unit
) : ListAdapter<DifferentiableItem, RecyclerView.ViewHolder>(DifferentiableItemDiffUtil.getInstance()) {

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
}
