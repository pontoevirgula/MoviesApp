package com.chslcompany.moviesapp.feature_movies.presentation.home.viewmodel

import com.example.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieListViewModelTest{

    @get:Rule
    var mainCoroutineDispatcher = MainCoroutineRule()

    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setup() {
        //viewModel = MovieListViewModel()
    }
}