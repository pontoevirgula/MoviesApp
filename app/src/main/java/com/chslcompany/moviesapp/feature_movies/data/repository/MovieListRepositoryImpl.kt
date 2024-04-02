package com.chslcompany.moviesapp.feature_movies.data.repository

import com.chslcompany.moviesapp.feature_movies.data.local.MovieDatabase
import com.chslcompany.moviesapp.feature_movies.data.remote.MoviesApi
import com.example.core.model.Movie
import com.example.core.repository.MovieListRepository
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val database: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): List<Movie> {

        val localMovieList = database.movieDao.getMovieListByCategory(category)
        val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote
        if (shouldLoadLocalMovie) {
            return localMovieList.map { movieDb ->
                movieDb.toMovie(category)
            }
        }

        return try {
            val getMovieFromApi = api.getMovieList(category, page)
            val saveMovieList = getMovieFromApi.results.map { movieDTO ->
                movieDTO.toMovieDb(category)
            }
            database.movieDao.upsertMovieList(saveMovieList)
            saveMovieList.map { it.toMovie(category) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getMovie(id: Int): Movie {
        val movieDb = database.movieDao.getMovieById(id)
        return movieDb.toMovie(movieDb.category)
    }

}