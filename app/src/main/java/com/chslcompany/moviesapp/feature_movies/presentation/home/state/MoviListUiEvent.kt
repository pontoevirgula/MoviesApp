package com.chslcompany.moviesapp.feature_movies.presentation.home.state

sealed interface MovieListUiEvent {
    data class Paginate(val category: String) : MovieListUiEvent
    data object Navigate : MovieListUiEvent
}