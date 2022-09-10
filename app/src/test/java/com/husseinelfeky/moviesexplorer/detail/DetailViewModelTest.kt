package com.husseinelfeky.moviesexplorer.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.husseinelfeky.moviesexplorer.data.FakeMoviesRepository
import com.husseinelfeky.moviesexplorer.model.MovieDto
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.ui.detail.DetailViewModel
import com.husseinelfeky.moviesexplorer.utils.FakeMovies
import com.husseinelfeky.moviesexplorer.utils.MainCoroutineRule
import com.husseinelfeky.moviesexplorer.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@ExperimentalCoroutinesApi
class DetailViewModelTest : AutoCloseKoinTest() {

    private lateinit var viewModel: DetailViewModel

    private lateinit var movieDto: MovieDto

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    /**
     * As we use Koin as a Service Locator library to develop the app, we'll also
     * use Koin to test the code. At this step we will initialize Koin related
     * code to be able to use it in our testing.
     */
    @Before
    fun init() {
        // Stop the original app Koin.
        stopKoin()
        movieDto = FakeMovies.getMovieDto()

        val testModule = module {
            viewModel {
                DetailViewModel(
                    moviesRepository = get(),
                    movieName = movieDto.title
                )
            }
            single<MoviesRepository> {
                FakeMoviesRepository(
                    moviesList = FakeMovies.getMoviesList()
                )
            }
        }

        startKoin {
            modules(listOf(testModule))
        }

        viewModel = get()
    }

    @Test
    fun getMovieDetails_isValid() {
        // GIVEN - A movie passed as a parameter name in the ViewModel constructor.

        // WHEN - Get movie details.
        val result = viewModel.movie.getOrAwaitValue()

        // THEN - Verify that the movie is valid and exists.
        assertThat(result, notNullValue())
        assertThat(result.movieName, `is`(movieDto.title))
    }
}
