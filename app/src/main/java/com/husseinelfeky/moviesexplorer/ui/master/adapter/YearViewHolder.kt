package com.husseinelfeky.moviesexplorer.ui.master.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.databinding.ItemYearBinding
import com.husseinelfeky.moviesexplorer.model.Year

class YearViewHolder(private val binding: ItemYearBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(year: Year) {
        binding.year = year.value
        binding.executePendingBindings()
    }

    companion object {
        fun create(container: ViewGroup): YearViewHolder {
            val binding = ItemYearBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return YearViewHolder(binding)
        }
    }
}
