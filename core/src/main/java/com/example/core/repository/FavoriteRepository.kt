package com.example.core.repository

import com.example.core.model.Movie
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun getFavorites() : Flow<Resource<List<Movie>>>
    suspend fun insert(movie: Movie)
    suspend fun delete(movie: Movie)
}