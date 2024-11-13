package com.chslcompany.moviesapp.feature_movies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chslcompany.moviesapp.feature_movies.presentation.details.screen.DetailsScreen
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel.FavoriteViewModel
import com.chslcompany.moviesapp.feature_movies.presentation.home.screen.HomeScreen
import com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel.MovieListViewModel
import com.chslcompany.moviesapp.feature_movies.presentation.theme.MoviesAppTheme
import com.chslcompany.moviesapp.feature_movies.util.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           val movieListViewModel = hiltViewModel<MovieListViewModel>()
           val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            val isDarkMode by movieListViewModel.isDarkMode.collectAsState(initial = false)
            MoviesAppTheme(darkTheme = isDarkMode) {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.rout
                    ) {
                        val trace = Firebase.performance.newTrace(STARTUP_TRACE_NAME)
                        Log.d("PERFOMANCE", "Starting trace")
                        trace.start()
                        composable(Screens.Home.rout) {
                            HomeScreen(navController, movieListViewModel, favoriteViewModel, isDarkMode)
                        }
                        composable(
                            route = Screens.Details.rout + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            DetailsScreen()
                        }
                        trace.stop()
                    }
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }

    companion object {
        private const val STARTUP_TRACE_NAME = "startup_trace"
    }

}