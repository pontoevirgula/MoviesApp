package com.chslcompany.moviesapp.feature_movies.presentation.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chslcompany.moviesapp.R
import com.chslcompany.moviesapp.feature_movies.presentation.BottomNavigationBar
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.screen.MyFavoriteMoviesScreen
import com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel.MovieListViewModel
import com.chslcompany.moviesapp.feature_movies.util.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = viewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onItemSelected = viewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = ShowTopAppBarTitle(
                            bottomNavController.currentBackStackEntry?.destination?.route
                        ),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screens.PopularMovieList.rout
            ) {
                composable(Screens.PopularMovieList.rout) {
                    PopularMovieScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = viewModel::onEvent
                    )
                }
                composable(Screens.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        navController = navController,
                        movieListState = movieListState,
                        onEvent = viewModel::onEvent
                    )
                }
                composable(Screens.FavoriteMovieList.rout) {
                    MyFavoriteMoviesScreen(
                        bottomNavController = bottomNavController,
                        navController = navController,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun ShowTopAppBarTitle(route: String?): String {
    return when (route) {
        Screens.PopularMovieList.rout ->
            stringResource(R.string.popular_movie)
        Screens.UpcomingMovieList.rout ->
            stringResource(R.string.upcoming_movie)
        else ->
            stringResource(R.string.my_favorites)
    }

}

