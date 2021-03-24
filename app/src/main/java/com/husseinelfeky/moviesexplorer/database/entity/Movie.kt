package com.husseinelfeky.moviesexplorer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val movieName: String,
    val rating: Int,
    val year: Int
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = movieName

    override fun getContent(): String = toString()
}
