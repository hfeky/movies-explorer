package com.husseinelfeky.moviesexplorer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey val genreName: String
)
