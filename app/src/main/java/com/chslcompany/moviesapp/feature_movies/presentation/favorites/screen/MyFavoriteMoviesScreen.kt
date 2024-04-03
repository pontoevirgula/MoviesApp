package com.chslcompany.moviesapp.feature_movies.presentation.favorites.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chslcompany.moviesapp.feature_movies.presentation.MovieItem
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel.FavoriteViewModel
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListUiEvent
import com.chslcompany.moviesapp.feature_movies.util.Category

@Composable
fun MyFavoriteMoviesScreen(
    bottomNavController : NavHostController,
    navController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
){
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
    val favoriteListState = favoriteViewModel.favoriteListState.collectAsState().value
    if (favoriteListState.favorites?.isEmpty() == true){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ){
            items(favoriteListState.favorites?.size!!){ index ->
                MovieItem(
                    bottomNavController = bottomNavController,
                    movie = favoriteListState.favorites[index],
                    navHostController = navController,
                    viewModel = favoriteViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (index >= favoriteListState.favorites.size - 1 && !favoriteListState.isLoading){
                    onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                }
            }
        }
    }
}