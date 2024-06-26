package com.chslcompany.moviesapp.feature_movies.presentation.home.state

import com.example.core.model.Movie

data class MovieListState(
    val isLoading : Boolean = false,

    val popularMovieListPage : Int = 1,
    val upcomingMovieListPage : Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val favoriteMovieList: List<Movie> = emptyList(),
)