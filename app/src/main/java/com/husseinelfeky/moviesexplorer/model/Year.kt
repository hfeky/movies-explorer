package com.husseinelfeky.moviesexplorer.model

import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem

data class Year(
    val value: Int
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = value

    override fun getContent(): String = toString()
}
