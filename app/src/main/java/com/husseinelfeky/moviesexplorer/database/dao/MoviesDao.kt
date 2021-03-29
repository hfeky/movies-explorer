package com.husseinelfeky.moviesexplorer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.husseinelfeky.moviesexplorer.database.crossref.MovieCastCrossRef
import com.husseinelfeky.moviesexplorer.database.crossref.MovieGenreCrossRef
import com.husseinelfeky.moviesexplorer.database.entity.CastMember
import com.husseinelfeky.moviesexplorer.database.entity.Genre
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.database.relation.MovieDetails
import com.husseinelfeky.moviesexplorer.model.MovieDto

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies ORDER BY movieName")
    fun getAllMovies(): LiveData<List<Movie>>

    /**
     * [COLLATE NOCASE] is used to ignore characters case.
     *
     * @return an observable list of the search query.
     */
    @Query(
        """
        SELECT * FROM movies
        WHERE movieName LIKE '%' || :query || '%'
        COLLATE NOCASE
        """
    )
    fun getMoviesByQuery(query: String): LiveData<List<Movie>>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieName = :name")
    suspend fun getMovieByName(name: String): MovieDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenre(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCastMember(castMember: CastMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieGenreCrossRef(movieGenreCrossRef: MovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieCastCrossRef(movieCastCrossRef: MovieCastCrossRef)

    @Transaction
    suspend fun saveMovie(movie: MovieDto) {
        movie.apply {
            saveMovie(Movie(title, rating, year))

            genres.forEach {
                saveGenre(Genre(it))
                saveMovieGenreCrossRef(MovieGenreCrossRef(title, it))
            }

            cast.forEach {
                saveCastMember(CastMember(it))
                saveMovieCastCrossRef(MovieCastCrossRef(title, it))
            }
        }
    }
}
