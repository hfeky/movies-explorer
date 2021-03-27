package com.husseinelfeky.moviesexplorer.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.databinding.ItemCastMemberBinding

class CastMemberViewHolder(
    private val binding: ItemCastMemberBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(castMember: CastMember) {
        binding.castMemberName = castMember.castMemberName
        binding.executePendingBindings()
    }

    companion object {
        fun create(container: ViewGroup): CastMemberViewHolder {
            val binding = ItemCastMemberBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return CastMemberViewHolder(binding)
        }
    }
}
