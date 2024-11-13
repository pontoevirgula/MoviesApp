package com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.moviesapp.feature_movies.data.repository.UserPreferenceRepository
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListState
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListUiEvent
import com.chslcompany.moviesapp.feature_movies.util.Category
import com.example.core.usecase.movielistusecase.GetMovieListUseCase
import com.example.core.util.Resource
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val useCase: GetMovieListUseCase,
    private val userPreferenceRepository : UserPreferenceRepository
) : ViewModel() {

    private val trace = Firebase.performance.newTrace(CALL_NETWORK)
    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    val isDarkMode = userPreferenceRepository.isDarkModeFlow

    init {
        trace.start()
        getPopularMovieList(forceFetchFromRemote = false)
        getUpcomingMovieList(forceFetchFromRemote = false)
    }

    fun setDarkMode(newValue: Boolean) {
        viewModelScope.launch {
            userPreferenceRepository.isDarkMode(newValue)
        }
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(forceFetchFromRemote = true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(forceFetchFromRemote = true)
                }
            }

        }
    }

    fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            useCase(
                GetMovieListUseCase.GetParams(
                    forceFetchFromRemote,
                    Category.UPCOMING,
                    movieListState.value.upcomingMovieListPage
                )
            ).flowOn(Dispatchers.IO)
                .collectLatest { result ->
                    Log.d(
                        "VIEWMODEL - UPCOMING",
                        "Incrementing number of requests counter in trace"
                    )
                    trace.incrementMetric(REQUESTS_COUNTER_NAME, 1)
                    when (result) {
                        is Resource.Error -> {
                            trace.stop()
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { upcomingList ->
                                _movieListState.update {
                                    it.copy(
                                        upcomingMovieList = movieListState.value.upcomingMovieList + upcomingList,
                                        upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                    )
                                }
                            }
                        }

                        else -> {}
                    }

                }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            useCase(
                GetMovieListUseCase.GetParams(
                    forceFetchFromRemote,
                    Category.POPULAR,
                    movieListState.value.popularMovieListPage
                )
            ).flowOn(Dispatchers.IO)
                .collectLatest { result ->
                    Log.d("VIEWMODEL - POPULAR", "Incrementing number of requests counter in trace")
                    trace.incrementMetric(REQUESTS_COUNTER_NAME, 1)
                    when (result) {
                        is Resource.Error -> {
                            trace.stop()
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { popularList ->
                                _movieListState.update {
                                    it.copy(
                                        popularMovieList = movieListState.value.popularMovieList + popularList,
                                        popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                    )
                                }
                            }
                        }

                        else -> {}
                    }

                }
        }
    }

    companion object {
        private const val REQUESTS_COUNTER_NAME = "requests sent"
        private const val CALL_NETWORK = "call network"
    }
}