package com.husseinelfeky.moviesexplorer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val name: String,
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = id

    override fun getContent(): String = toString()
}
