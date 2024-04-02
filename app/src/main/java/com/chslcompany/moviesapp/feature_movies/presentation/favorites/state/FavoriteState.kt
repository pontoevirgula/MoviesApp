package com.chslcompany.moviesapp.feature_movies.presentation.favorites.state

import com.example.core.model.Movie

data class FavoriteState (
    val isLoading: Boolean = false,
    val favorites : List<Movie>? = emptyList()
)