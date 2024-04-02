package com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel

import com.example.core.usecase.movielistusecase.GetMovieListUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.MovieFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    @get:Rule
    var mainCoroutineDispatcher = MainCoroutineRule()

    private lateinit var viewModel: MovieListViewModel

    private val movieFactory = MovieFactory().create(MovieFactory.Movie.MovieSuccess)

    @Mock
    lateinit var useCase: GetMovieListUseCase

    @Before
    fun setup() {
        viewModel = MovieListViewModel(useCase)
    }

    @Test
    fun `Should validate success data`() = runTest {
        whenever(useCase(any())).thenReturn(movieFactory)
        val result = viewModel.movieListState.value
        assertNotNull(result.popularMovieList.first())

    }

    @Test(expected = RuntimeException::class)
    fun `Should validate error data`() = runTest {
        whenever(useCase(any())).thenThrow(RuntimeException())
        viewModel.movieListState.value
        //assertNotNull(result.popularMovieList.first())
    }


}