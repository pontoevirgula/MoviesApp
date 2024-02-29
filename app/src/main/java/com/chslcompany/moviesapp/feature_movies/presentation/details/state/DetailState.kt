package com.chslcompany.moviesapp.feature_movies.presentation.details.state

import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)