package com.husseinelfeky.moviesexplorer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem

@Entity(tableName = "cast")
data class CastMember(
    @PrimaryKey val castMemberName: String
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = castMemberName

    override fun getContent(): String = toString()
}
