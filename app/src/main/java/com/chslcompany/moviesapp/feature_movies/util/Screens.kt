package com.chslcompany.moviesapp.feature_movies.util

sealed class Screens(val rout: String) {
    data object Home : Screens("main")
    data object PopularMovieList : Screens("popularMovie")
    data object UpcomingMovieList : Screens("upcomingMovie")
    data object FavoriteMovieList : Screens("favoriteMovie")
    data object Details : Screens("details")
}

