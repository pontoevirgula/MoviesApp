package com.chslcompany.moviesapp.core.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chslcompany.moviesapp.core.ui.theme.MoviesAppTheme
import com.chslcompany.moviesapp.feature_movies.presentation.details.screen.DetailsScreen
import com.chslcompany.moviesapp.feature_movies.presentation.home.screen.HomeScreen
import com.chslcompany.moviesapp.feature_movies.util.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.rout
                    ){
                        composable(Screens.Home.rout){
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screens.Details.rout+"/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ){
                            DetailsScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color){
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color){
            systemUiController.setSystemBarsColor(color)
        }
    }
}