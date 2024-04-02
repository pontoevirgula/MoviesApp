package com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListState
import com.chslcompany.moviesapp.feature_movies.presentation.home.state.MovieListUiEvent
import com.chslcompany.moviesapp.feature_movies.util.Category
import com.example.core.usecase.movielistusecase.GetMovieListUseCase
import com.example.core.util.Resource
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
    private val useCase: GetMovieListUseCase
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(forceFetchFromRemote = false)
        getUpcomingMovieList(forceFetchFromRemote = false)
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

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
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
                    when (result) {
                        is Resource.Error -> {
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
                                        upcomingMovieList = movieListState.value.upcomingMovieList + upcomingList.shuffled(),
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
                    when (result) {
                        is Resource.Error -> {
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
                                        popularMovieList = movieListState.value.popularMovieList + popularList.shuffled(),
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
}