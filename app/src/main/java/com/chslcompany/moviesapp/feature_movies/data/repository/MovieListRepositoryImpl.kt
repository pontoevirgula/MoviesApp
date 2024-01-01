package com.chslcompany.moviesapp.feature_movies.data.repository

import com.chslcompany.moviesapp.core.util.Resource
import com.chslcompany.moviesapp.feature_movies.data.local.MovieDatabase
import com.chslcompany.moviesapp.feature_movies.data.remote.MoviesApi
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie
import com.chslcompany.moviesapp.feature_movies.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = database.movieDao.getMovieListByCategory(category)
            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieDb ->
                        movieDb.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            try {
                val getMovieFromApi = api.getMovieList(category, page)
                val saveMovieList = getMovieFromApi.results.map { movieDTO ->
                    movieDTO.toMovieDb(category)
                }
                database.movieDao.upsertMovieList(saveMovieList)
                emit(Resource.Success(
                    data = saveMovieList.map { it.toMovie(category) }
                ))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error ao carregar filmes"))
                return@flow
            }
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieDb = database.movieDao.getMovieById(id)

            if (movieDb != null) {
                emit(Resource.Success(movieDb.toMovie(movieDb.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error(message = "Error ao carregar busca por filmes"))
            emit(Resource.Loading(false))
            return@flow
        }
    }
}