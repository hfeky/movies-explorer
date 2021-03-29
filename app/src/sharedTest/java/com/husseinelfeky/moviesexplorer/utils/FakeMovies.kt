package com.husseinelfeky.moviesexplorer.utils

import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.database.entity.Genre
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieDto

object FakeMovies {

    fun getMovieDto(): MovieDto {
        return MovieDto(
            title = "Title 1",
            rating = 5,
            year = 2018,
            genres = listOf(
                "Genre 1",
                "Genre 2"
            ),
            cast = listOf(
                "Cast Member 1",
                "Cast Member 2",
                "Cast Member 3"
            )
        )
    }

    fun getMovieDetails(): MovieDetails {
        return MovieDetails(
            movieName = "Title 1",
            rating = 5,
            year = 2018,
            genres = listOf(
                Genre("Genre 1"),
                Genre("Genre 2")
            ),
            cast = listOf(
                CastMember("Cast Member 1"),
                CastMember("Cast Member 2"),
                CastMember("Cast Member 3")
            )
        )
    }

    fun getMoviesList(): List<Movie> {
        return listOf(
            Movie("Title 1", 5, 2018),
            Movie("Title 2", 3, 2016),
            Movie("Title 3", 2, 2016),
            Movie("Title 4", 5, 2014),
            Movie("Title 5", 6, 2016),
            Movie("Title 6", 4, 2014),
            Movie("Title 7", 5, 2016),
            Movie("Title 8", 4, 2014),
            Movie("Title 9", 4, 2016),
            Movie("Title 10", 1, 2018),
            Movie("Title 11", 3, 2018),
            Movie("Title 12", 2, 2018),
            Movie("Title 13", 1, 2018),
            Movie("Title 14", 5, 2016),
            Movie("Title 15", 4, 2018)
        )
    }
}
