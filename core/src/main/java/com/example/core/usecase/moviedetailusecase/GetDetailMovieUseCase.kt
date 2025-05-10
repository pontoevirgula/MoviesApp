package com.example.core.usecase.moviedetailusecase

import com.example.core.model.Movie
import com.example.core.repository.MovieListRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetDetailMovieUseCase{
    operator fun invoke(params: GetParams): Flow<Resource<Movie>>
    data class GetParams(val id: Int)
}

class GetDetailMovieUseCaseImpl @Inject constructor(
    private val repository: MovieListRepository
) : GetDetailMovieUseCase {
    override fun invoke(params: GetDetailMovieUseCase.GetParams): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieId = repository.getMovieById(params.id)

            emit(Resource.Success(movieId))
            emit(Resource.Loading(false))
            return@flow
        }
    }
}