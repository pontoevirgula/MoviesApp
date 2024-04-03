package com.chslcompany.moviesapp.feature_movies.data.repository

import com.chslcompany.moviesapp.feature_movies.data.local.favorite.FavoriteDao
import com.chslcompany.moviesapp.feature_movies.data.local.favorite.FavoriteEntity
import com.example.core.model.Movie
import com.example.core.repository.FavoriteRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {
    override suspend fun getFavorites(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val favorites = dao.listFavorites().map { it.toMovieFromFavoriteDb() }

                emit( Resource.Success(data = favorites))
                emit(Resource.Loading(false))
                return@flow
            } catch (e : Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error ao carregar favoritos"))
                return@flow
            }
        }
    }

    override suspend fun insert(movie: Movie) {
        dao.insertFavorite(
            FavoriteEntity(
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                overview = movie.overview,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                title = movie.title,
                vote_average = movie.vote_average,
                id = movie.id,
                adult = false,
                genre_ids = "",
                original_title = "",
                popularity = 0.0,
                video = false,
                vote_count = 1
            )
        )
        Resource.Success(data = movie)
    }

    override suspend fun delete(movie: Movie) {
        dao.deleteFavoriteById(
            FavoriteEntity(
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                overview = movie.overview,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                title = movie.title,
                vote_average = movie.vote_average,
                id = movie.id,
                adult = false,
                genre_ids = "",
                original_title = "",
                popularity = 0.0,
                video = false,
                vote_count = 1
            )
        )
        Resource.Success(movie)
    }
}

