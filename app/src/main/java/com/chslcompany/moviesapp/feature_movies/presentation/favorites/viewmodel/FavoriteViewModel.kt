package com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.state.FavoriteListState
import com.example.core.model.Movie
import com.example.core.repository.FavoriteRepository
import com.example.core.util.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
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
    private val trace = Firebase.performance.newTrace(CALL_NETWORK)
    private var _favoriteListState = MutableStateFlow(FavoriteListState())
    val favoriteListState = _favoriteListState.asStateFlow()

    init {
        trace.start()
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteListState.update {
                it.copy(isLoading = true)
            }
            repository.getFavorites()
                .collectLatest { favoriteList ->
                    Log.d(
                        "VIEWMODEL - FAVORITE",
                        "Incrementing number of requests counter in trace"
                    )
                    trace.incrementMetric(REQUESTS_COUNTER_NAME, 1)
                    when (favoriteList) {
                        is Resource.Error -> {
                            trace.stop()
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

    companion object {
        private const val REQUESTS_COUNTER_NAME = "requests sent"
        private const val CALL_NETWORK = "call network"
    }
}