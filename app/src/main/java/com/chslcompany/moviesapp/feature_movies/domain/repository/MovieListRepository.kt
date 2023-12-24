package com.chslcompany.moviesapp.feature_movies.domain.repository

import com.chslcompany.moviesapp.core.util.Resource
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        category: String,
        page: Int
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(id : Int) : Flow<Resource<Movie>>
}