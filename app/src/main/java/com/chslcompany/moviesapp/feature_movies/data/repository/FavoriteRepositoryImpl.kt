package com.chslcompany.moviesapp.feature_movies.data.repository

import com.chslcompany.moviesapp.core.util.Resource
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDao
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie
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
