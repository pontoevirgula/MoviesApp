package com.chslcompany.moviesapp.core.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.chslcompany.moviesapp.R
import com.chslcompany.moviesapp.feature_movies.presentation.state.MovieListUiEvent
import com.chslcompany.moviesapp.feature_movies.util.Screen

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onItemSelected: (MovieListUiEvent) -> Unit
) {

    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ), BottomNavigationItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                onItemSelected(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularMovieList.rout)
                            }

                            1 -> {
                                onItemSelected(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                            }
                        }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }

}

data class BottomNavigationItem(
    val title: String, val icon: ImageVector
)