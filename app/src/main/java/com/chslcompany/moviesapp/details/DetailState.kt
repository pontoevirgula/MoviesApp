package com.chslcompany.moviesapp.details

import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)