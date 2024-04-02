package com.chslcompany.moviesapp.feature_movies.data.repository

import com.example.core.util.Resource
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDao
import com.example.core.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: MovieDao
) : FavoriteRepository {
    override suspend fun getFavorites(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(true))
        try {
            //val favorites = dao.
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Error(message = "Error ao carregar favoritos"))
            return@flow
        }
    }
}

interface FavoriteRepository {
    suspend fun getFavorites(): Flow<Resource<List<Movie>>>
}
