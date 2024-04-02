package com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.state.FavoriteState
import com.example.core.model.Movie
import com.example.core.repository.FavoriteRepository
import com.example.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {
    private var _favoriteListState = MutableStateFlow(FavoriteState())
    val favoriteListState = _favoriteListState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteListState.update {
                it.copy(isLoading = true)
            }
            repository.getFavorites()
                .collectLatest { favoriteList ->
                    when (favoriteList) {
                        is Resource.Error -> {
                            _favoriteListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _favoriteListState.update {
                                it.copy(isLoading = favoriteList.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            favoriteList.data?.let { favorites ->
                                _favoriteListState.update {
                                    it.copy(
                                        isLoading = false,
                                        favorites = favorites
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    fun addFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }
}