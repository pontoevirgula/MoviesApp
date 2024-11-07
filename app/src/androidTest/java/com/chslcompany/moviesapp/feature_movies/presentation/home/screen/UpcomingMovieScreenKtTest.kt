package com.chslcompany.moviesapp.feature_movies.presentation.home.screen

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.chslcompany.CleanDataStoreTestRule
import com.chslcompany.moviesapp.feature_movies.presentation.MainActivity
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListState
import com.example.core.model.Movie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UpcomingMovieScreenKtTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val cleanDataStoreTestRule = CleanDataStoreTestRule()

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clearAndSetContent(content: @Composable () -> Unit) {
        (this.activity.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0) as? ComposeView)?.setContent(content)
            ?: this.setContent(content)
    }

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun showsLoadingIndicator_whenMovieListIsEmpty(){
        composeTestRule.clearAndSetContent {
            UpcomingMoviesScreen(
                movieListState = MovieListState(upcomingMovieList = emptyList(), isLoading = true),
                navController = rememberNavController(),
                onEvent = {}
            )
        }

        composeTestRule
            .onNodeWithTag("LoadingIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun showMovies_whenMovieListIsNotEmpty(){
        val movieList = listOf(
            Movie("sjgid", 1,"","bom","dkshkjsdsf","20/02/2025","a culpa",4.45, "popular"),
            Movie("sjgid2", 2,"","fraco","dkshkjsdsf","20/04/2025","a culpa2",3.45, "popular"),
            Movie("sjgidss2", 22,"","fraco demais","dkshkjsdsf","22/04/2025","a culpa3",3.45, "popular"),
        )
        composeTestRule.clearAndSetContent {
            UpcomingMoviesScreen(
                movieListState = MovieListState(upcomingMovieList = movieList, isLoading = false),
                navController = rememberNavController(),
                onEvent = {}
            )
        }
        composeTestRule
            .onNodeWithTag("MovieItem")
            .assertIsDisplayed()
    }
}
