package com.husseinelfeky.moviesexplorer.master

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.husseinelfeky.moviesexplorer.data.FakeMoviesRepository
import com.husseinelfeky.moviesexplorer.database.entity.Movie
import com.husseinelfeky.moviesexplorer.model.Year
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.ui.master.MasterViewModel
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
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@ExperimentalCoroutinesApi
class MainViewModelTest : AutoCloseKoinTest() {

    private lateinit var viewModel: MasterViewModel

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
                MasterViewModel(
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
    }

    @Test
    fun getAllMovies_moviesExist() {
        // GIVEN - Movies repository.

        // WHEN - Get all movies.
        val result = viewModel.getAllMovies().getOrAwaitValue()

        // THEN - Verify that movies exist and are fetched successfully.
        assertThat(result, `is`(notNullValue()))
        assertThat(result.size, `is`(0))
    }

    @Test
    fun searchMovies_resultsFound() {
        // GIVEN - A search query.
        val searchQuery = "Title 1"

        // WHEN - Search movies by the search query "Title 1".
        val result = viewModel.searchMoviesByName(searchQuery).getOrAwaitValue()

        // THEN - Verify that search results are found and contain 8 differentiable items
        // (2 years and 6 movies).
        assertThat(result, `is`(notNullValue()))

        assertThat(result.size, `is`(8))

        var expectedMoviesCount = 6
        var expectedYearsCount = 2

        for (item in result) {
            if (item is Movie) {
                expectedMoviesCount--
            } else if (item is Year) {
                expectedYearsCount--
            }
        }

        assertThat(expectedMoviesCount, `is`(0))
        assertThat(expectedYearsCount, `is`(0))
    }

    @Test
    fun searchMovies_noResultsFound() {
        // GIVEN - A search query.
        val searchQuery = "Title 20"

        // WHEN - Search movies by the search query "Title 20".
        val result = viewModel.searchMoviesByName(searchQuery).getOrAwaitValue()

        // THEN - Verify that no search results are found.
        assertThat(result, `is`(emptyList()))
    }
}
