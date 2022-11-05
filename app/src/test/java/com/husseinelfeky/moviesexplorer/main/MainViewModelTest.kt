package com.husseinelfeky.moviesexplorer.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.husseinelfeky.moviesexplorer.data.FakeMoviesRepository
import com.husseinelfeky.moviesexplorer.model.Result
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.ui.main.MainViewModel
import com.husseinelfeky.moviesexplorer.utils.FakeMovies
import com.husseinelfeky.moviesexplorer.utils.MainCoroutineRule
import com.husseinelfeky.moviesexplorer.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@ExperimentalCoroutinesApi
class MainViewModelTest : AutoCloseKoinTest() {

    private lateinit var viewModel: MainViewModel
    private lateinit var moviesRepository: FakeMoviesRepository

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
        val testModule = module {
            viewModel {
                MainViewModel(
                    moviesRepository = get()
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
        moviesRepository = get<MoviesRepository>() as FakeMoviesRepository
    }

    @Test
    fun getMovieImages_returnError() = mainCoroutineRule.runBlockingTest {
        // GIVEN - A movie name.
        val movieName = "Title 1"

        // WHEN - An error occurs when fetching the movie images.
        moviesRepository.setReturnError(true)
        viewModel.getMovieImages(movieName)

        // THEN - Verify that an error occurred.
        val result = viewModel.movieImages.getOrAwaitValue()

        assertThat(result, notNullValue())
        assertThat(result.status, `is`(Result.Status.ERROR))
        assertThat(result.message, `is`("Failed to fetch movie images."))

        assertThat(viewModel.imagesLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun getMovieImages_noImagesFound() = mainCoroutineRule.runBlockingTest {
        // GIVEN - A movie name.
        val movieName = "Title 1"

        // WHEN - Fetch the movie images.
        viewModel.getMovieImages(movieName)

        // THEN - Verify that no error occurred and a successful result is returned.
        val result = viewModel.movieImages.getOrAwaitValue()

        assertThat(result, notNullValue())
        assertThat(result.status, `is`(Result.Status.ERROR))
        assertThat(result.message, `is`("No images were found."))

        assertThat(viewModel.imagesLoading.getOrAwaitValue(), `is`(false))
    }
}
