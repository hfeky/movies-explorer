package com.husseinelfeky.moviesexplorer.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.husseinelfeky.moviesexplorer.database.LocalDb
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.MovieDto
import com.husseinelfeky.moviesexplorer.utils.FakeMovies
import com.husseinelfeky.moviesexplorer.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

// Unit test the DAO.
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

    private lateinit var database: LocalDb
    private lateinit var moviesDao: MoviesDao

    private lateinit var movieDto: MovieDto

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDb::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()

        moviesDao = database.moviesDao()

        movieDto = FakeMovies.getMovieDto()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun saveMovie_getName() = runBlocking {
        // GIVEN - Save a movie.
        moviesDao.saveMovie(movieDto)

        // WHEN - Get the movie by name from the database.
        val result = moviesDao.getMovieByName(movieDto.title)

        // THEN - The loaded data contains the expected values.
        assertThat(result, notNullValue())
        assertThat(result.movieName, `is`(movieDto.title))
        assertThat(result.rating, `is`(movieDto.rating))
        assertThat(result.year, `is`(movieDto.year))
        assertThat(result.genres.map { it.genreName }, `is`(movieDto.genres))
        assertThat(result.cast.map { it.castMemberName }, `is`(movieDto.cast))
    }

    @Test
    fun getAllMovies_returnResult() = runBlocking {
        // GIVEN - Save 15 movies.
        val moviesList = FakeMovies.getMoviesList()
        moviesList.forEach {
            moviesDao.saveMovie(it)
        }

        // WHEN - Get all saved movies.
        val result = moviesDao.getAllMovies().getOrAwaitValue()

        // THEN - Return all saved movies as they were.
        assertThat(result.size, `is`(moviesList.size))
        assertThat(result, hasItem(movieDto.toDomainModel()))
    }

    @Test
    fun searchMovies_returnResult() = runBlocking {
        // GIVEN - Save a movie with title "Title 1".
        moviesDao.saveMovie(movieDto)

        // WHEN - Get a movie by name "Title 1".
        val result = moviesDao.getMoviesByQuery("Title 1").getOrAwaitValue()

        // THEN - Return the movie.
        assertThat(result, hasItem(movieDto.toDomainModel()))
    }

    @Test
    fun searchMovies_returnEmptyList() = runBlocking {
        // GIVEN - Save a movie with title "Title 1".
        moviesDao.saveMovie(movieDto)

        // WHEN - Get a movie by name "Title 2".
        val result = moviesDao.getMoviesByQuery("Title 2").getOrAwaitValue()

        // THEN - Return empty list.
        assertThat(result, `is`(emptyList<List<Movie>>()))
    }
}
