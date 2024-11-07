package com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel

import com.chslcompany.moviesapp.feature_movies.data.repository.UserPreferenceRepository
import com.example.core.model.Movie
import com.example.core.usecase.movielistusecase.GetMovieListUseCase
import com.example.core.util.Resource
import com.example.testing.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class MovieViewModelTest {
    private lateinit var viewModel : MovieListViewModel
    private val mockUseCase = mockk<GetMovieListUseCase>(relaxed=true)
    private val mockUserPreferenceRepository = mockk<UserPreferenceRepository>(relaxed=true)
    @get:Rule
    var mainCoroutineDispatcher = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = MovieListViewModel(mockUseCase, mockUserPreferenceRepository)
    }

    @Test
    fun `getUpcomingMovieList should update state to loading, then success`() = runTest {
        val upcomingMovies = listOf(
            Movie("sjgid", 1,"","bom","dkshkjsdsf","20/02/2025","a culpa",4.45, "popular"),
            Movie("sjgid2", 2,"","fraco","dkshkjsdsf","20/04/2025","a culpa2",3.45, "popular"),
        )
        val result = Resource.Success(upcomingMovies)

        coEvery { mockUseCase(any()) } returns flowOf(result)

        viewModel.getUpcomingMovieList(false)

        delay(2000)

        assertEquals(true, viewModel.movieListState.value.isLoading)
        assertEquals(upcomingMovies, viewModel.movieListState.value.upcomingMovieList)
        assertEquals(2, viewModel.movieListState.value.upcomingMovieListPage)
    }

    @Test
    fun `getUpcomingMovieList should update state to error when resource is error`() = runTest {
        val result = Resource.Error<List<Movie>>("Error ocurred")

        coEvery { mockUseCase(any()) } returns flowOf(result)

        viewModel.getUpcomingMovieList(false)

        assertEquals(listOf(), viewModel.movieListState.value.upcomingMovieList)
    }

    @Test
    fun `getUpcomingMovieList should update loading state correctly when resource is loading`() = runTest {
        val result = Resource.Loading<List<Movie>>(isLoading = true)

        coEvery { mockUseCase(any()) } returns flowOf(result)

        viewModel.getUpcomingMovieList(false)

        assertEquals(true, viewModel.movieListState.value.isLoading)
    }
}
