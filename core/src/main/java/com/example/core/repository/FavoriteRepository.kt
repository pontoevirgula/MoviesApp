package com.example.core.repository

import com.example.core.model.Movie
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun getFavorites() : Flow<Resource<List<Movie>>>
    suspend fun insert(product: Movie)
    suspend fun delete(product: Movie)
}