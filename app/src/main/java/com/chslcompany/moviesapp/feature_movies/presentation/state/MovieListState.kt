package com.chslcompany.moviesapp.feature_movies.presentation.state

import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

data class MovieListState(
    val isLoading : Boolean = false,

    val popularMovieListPage : Int = 1,
    val upcomingMovieListPage : Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)