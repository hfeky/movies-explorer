package com.husseinelfeky.moviesexplorer.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.husseinelfeky.moviesexplorer.database.LocalDb
import com.husseinelfeky.moviesexplorer.database.dao.MoviesDao
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.MovieDto
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.network.FlickrApiService
import com.husseinelfeky.moviesexplorer.repository.MoviesRepositoryImpl
import com.husseinelfeky.moviesexplorer.utils.FakeMovies
import com.husseinelfeky.moviesexplorer.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executors

// Medium Test to test the repository.
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class MoviesRepositoryImplTest {

    private lateinit var database: LocalDb
    private lateinit var moviesDao: MoviesDao
    private lateinit var moviesRepository: MoviesRepositoryImpl

    private lateinit var movieDto: MovieDto

    @Mock
    private lateinit var flickrApiService: FlickrApiService

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDb::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .allowMainThreadQueries()
            .build()

        moviesDao = database.moviesDao()
        moviesRepository = MoviesRepositoryImpl(moviesDao, flickrApiService)

        movieDto = FakeMovies.getMovieDto()
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveMovie_retrievesMovie() = runBlocking {
        // GIVEN - a new movie saved in the database.
        moviesDao.saveMovie(movieDto)

        // WHEN - Movie retrieved by name.
        val result = moviesRepository.getMovieByName(movieDto.title)

        // THEN - Same reminder is returned.
        assertThat(result, notNullValue())
        assertThat(result.movieName, `is`(movieDto.title))
        assertThat(result.rating, `is`(movieDto.rating))
        assertThat(result.year, `is`(movieDto.year))
        assertThat(result.genres.map { it.genreName }, `is`(movieDto.genres))
        assertThat(result.cast.map { it.castMemberName }, `is`(movieDto.cast))
    }

    @Test
    fun saveManyMovies_returnAllMovies() = runBlocking {
        // GIVEN - Save 15 movies.
        val moviesList = FakeMovies.getMoviesList()
        moviesList.forEach {
            moviesDao.saveMovie(it)
        }

        // WHEN - Get all saved movies.
        val result = moviesRepository.getAllMovies().getOrAwaitValue()

        // THEN - Return all saved movies as they were.
        assertThat(result.size, `is`(moviesList.size))
        assertThat(result, hasItem(movieDto.toDomainModel()))
    }

    @Test
    fun searchMovies_returnSearchResults() = runBlocking {
        // GIVEN - Save 15 movies.
        val moviesList = FakeMovies.getMoviesList()
        moviesList.forEach {
            moviesDao.saveMovie(it)
        }

        // WHEN - Search movies with name "Title 1".
        val result = moviesRepository.searchMoviesByName("Title 1").getOrAwaitValue()

        // THEN - Return 6 movies (5 of year 2018 and 1 of year 2016).
        assertThat(result.size, `is`(2))
        assertThat(result[0].movies.size, `is`(5))
        assertThat(result[1].movies.size, `is`(1))
        assertThat(result[0].movies, hasItem(movieDto.toDomainModel()))
    }

    @Test
    fun searchMovies_returnEmptyList() = runBlocking {
        // GIVEN - Save a movie with title "Title 1".
        moviesDao.saveMovie(movieDto)

        // WHEN - Get a movie by name "Title 2".
        val result = moviesRepository.searchMoviesByName("Title 2").getOrAwaitValue()

        // THEN - Return empty list.
        assertThat(result, `is`(emptyList<List<Movie>>()))
    }

    @Test
    fun getMovieImages_returnImages(): Unit = runBlocking {
        // GIVEN - A movie name.
        val movieName = "Evil Dead"

        // WHEN - Fetch "Evil Dead" movie images.
        val apiResult = moviesRepository.getMovieImages(movieName)

        // THEN - First result is LOADING and second result is SUCCESS.
        Mockito.`when`(
            apiResult.take(2).toList().map { it.status }
        ).thenReturn(
            listOf(Result.Status.LOADING, Result.Status.SUCCESS)
        )
    }
}
