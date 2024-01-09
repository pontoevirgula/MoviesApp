package com.chslcompany.moviesapp.feature_movies.presentation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chslcompany.moviesapp.R
import com.chslcompany.moviesapp.feature_movies.presentation.viewmodel.MovieListViewModel
import com.chslcompany.moviesapp.feature_movies.util.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
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
                        text = if (movieListState.isCurrentPopularScreen)
                            stringResource(R.string.popular_movie)
                        else
                            stringResource(R.string.upcoming_movie),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ){
            NavHost(navController = bottomNavController, startDestination = Screen.PopularMovieList.rout){
                composable(Screen.PopularMovieList.rout){
                    //PopularMovieScreen()
                }
                composable(Screen.UpcomingMovieList.rout){
                    //PopularMovieScreen()
                }
            }
        }
    }
}

