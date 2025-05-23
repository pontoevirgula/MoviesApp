package com.chslcompany.moviesapp.feature_movies.presentation.favorites.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chslcompany.moviesapp.feature_movies.presentation.MovieItem
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.state.FavoriteListState
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel.FavoriteViewModel

@Composable
fun MyFavoriteMoviesScreen(
    bottomNavController : NavHostController,
    navController: NavHostController,
    favoriteListState: FavoriteListState,
    favoriteViewModel: FavoriteViewModel
){
    if (favoriteListState.favorites?.isEmpty() == true){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Não tem favoritos", color = MaterialTheme.colorScheme.onBackground)
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
                    favoriteViewModel = favoriteViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}