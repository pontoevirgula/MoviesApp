package com.chslcompany.moviesapp.feature_movies.presentation.details.state

import com.example.core.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)