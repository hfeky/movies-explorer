package com.husseinelfeky.moviesexplorer

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.husseinelfeky.moviesexplorer.database.LocalDb
import com.husseinelfeky.moviesexplorer.database.MoviesProvider
import com.husseinelfeky.moviesexplorer.koin.repositoriesModule
import com.husseinelfeky.moviesexplorer.koin.servicesModule
import com.husseinelfeky.moviesexplorer.koin.viewModelsModule
import com.husseinelfeky.moviesexplorer.repository.MoviesRepository
import com.husseinelfeky.moviesexplorer.ui.main.MainActivity
import com.husseinelfeky.moviesexplorer.utils.DataBindingIdlingResource
import com.husseinelfeky.moviesexplorer.utils.EspressoIdlingResource
import com.husseinelfeky.moviesexplorer.utils.monitorActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

// End-to-end test to black box test the app.
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest : AutoCloseKoinTest() {

    private lateinit var repository: MoviesRepository
    private lateinit var appContext: Application

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * As we use Koin as a Service Locator library to develop the app, we'll also
     * use Koin to test the code. At this step we will initialize Koin related
     * code to be able to use it in our testing.
     */
    @Before
    fun init() {
        // Stop the original app Koin.
        stopKoin()
        appContext = getApplicationContext()

        val databaseModule = module {
            single {
                var database: LocalDb? = null

                database = Room.databaseBuilder(
                    appContext,
                    LocalDb::class.java,
                    LocalDb::class.java.simpleName
                ).fallbackToDestructiveMigration()
                    // Populate the database with the data in movies.json file.
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            MoviesProvider.populateJsonData(appContext, get(), database)
                        }
                    })
                    .build()

                database
            }
            single { get<LocalDb>().moviesDao() }
        }

        // Declare a new Koin module.
        startKoin {
            modules(
                listOf(
                    databaseModule,
                    repositoriesModule,
                    viewModelsModule,
                    servicesModule
                )
            )
        }

        // Get our real repository.
        repository = get()
    }

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().apply {
            register(EspressoIdlingResource.countingIdlingResource)
            register(dataBindingIdlingResource)
        }
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().apply {
            unregister(EspressoIdlingResource.countingIdlingResource)
            unregister(dataBindingIdlingResource)
        }
    }

    @Test
    fun launchMainActivity_scrollToMovie() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)

        // MasterFragment: Attempt to scroll to the movie with the name "Evil Dead".
        onView(withId(R.id.rv_movies)).perform(
            // scrollTo will fail the test if no item matches.
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText("Evil Dead"))
            )
        )
    }

    @Test
    fun launchMainActivity_openMovieDetails_navigateBack() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)

        // MasterFragment: Click on the movie "Evil Dead".
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Evil Dead")),
                click()
            )
        )

        // DetailFragment: Navigate back.
        Espresso.pressBack()
    }

    @Test
    fun launchMainActivity_searchMovie_showNoResults() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)

        // MasterFragment: Click on search menu item.
        onView(withId(R.id.action_search)).perform(click())

        // MasterFragment: Type "Godzilla: King of the Monsters" in the SearchView.
        onView(withHint(R.string.hint_search_movie)).perform(typeText("Godzilla: King of the Monsters"))

        // MasterFragment: Click on search button on the keyboard.
        onView(withHint(R.string.hint_search_movie)).perform(pressImeActionButton())

        // MasterFragment: Delay one second to allow for the debounce operation to perform the search.
        runBlocking { delay(1000) }

        // MasterFragment: Verify that no search results are found.
        onView(withId(R.id.iv_no_search_results)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun launchMainActivity_searchMovie_showResults() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)

        // MasterFragment: Click on search menu item.
        onView(withId(R.id.action_search)).perform(click())

        // MasterFragment: Type "Godzilla" in the SearchView.
        onView(withHint(R.string.hint_search_movie)).perform(typeText("Godzilla"))

        // MasterFragment: Click on search button on the keyboard.
        onView(withHint(R.string.hint_search_movie)).perform(pressImeActionButton())

        // MasterFragment: Delay one second to allow for the debounce operation to perform the search.
        runBlocking { delay(1000) }

        // MasterFragment: Verify that no search results layout is hidden.
        onView(withId(R.id.iv_no_search_results)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        // MasterFragment: Click on the movie "Godzilla".
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Godzilla")),
                click()
            )
        )

        // DetailFragment: Navigate back.
        Espresso.pressBack()
    }
}
