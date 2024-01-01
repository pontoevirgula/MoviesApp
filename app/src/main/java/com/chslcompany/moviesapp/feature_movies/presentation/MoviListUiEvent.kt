package com.chslcompany.moviesapp.feature_movies.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    data object Navigate : MovieListUiEvent
}