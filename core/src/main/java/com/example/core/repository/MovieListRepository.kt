package com.example.core.repository

import com.example.core.model.Movie

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        category: String,
        page: Int
    ) : List<Movie>

    suspend fun getMovieById(id : Int) : Movie
}