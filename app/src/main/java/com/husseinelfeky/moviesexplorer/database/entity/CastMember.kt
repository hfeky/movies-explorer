package com.husseinelfeky.moviesexplorer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class CastMember(
    @PrimaryKey val castMemberName: String
)
