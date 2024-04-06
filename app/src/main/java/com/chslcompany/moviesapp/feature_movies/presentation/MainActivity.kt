package com.chslcompany.moviesapp.feature_movies.presentation

import android.content.Context
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
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chslcompany.moviesapp.feature_movies.presentation.details.screen.DetailsScreen
import com.chslcompany.moviesapp.feature_movies.presentation.home.screen.HomeScreen
import com.chslcompany.moviesapp.feature_movies.presentation.theme.MoviesAppTheme
import com.chslcompany.moviesapp.feature_movies.util.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val Context.dataStore by preferencesDataStore(
        name = "user_preferences",
    )
    private val darkModePreference = booleanPreferencesKey("user_preferences")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkModeFlow = dataStore.data.catch { exception ->
                if (exception is IOException) {
                    Log.e("ERROR_DATA STORE", "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[darkModePreference] ?: false
            }
            val isDarkMode by isDarkModeFlow.collectAsState(initial = false)
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
                        composable(Screens.Home.rout) {
                            HomeScreen(navController, dataStore, darkModePreference, isDarkMode)
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

}