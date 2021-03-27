package com.husseinelfeky.moviesexplorer.ui.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItemDiffUtil

class CastAdapter : ListAdapter<CastMember, CastMemberViewHolder>(
    DifferentiableItemDiffUtil.getInstance()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMemberViewHolder {
        return CastMemberViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CastMemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
