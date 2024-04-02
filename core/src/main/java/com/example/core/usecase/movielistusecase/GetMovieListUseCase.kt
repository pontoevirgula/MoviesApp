package com.example.core.usecase.movielistusecase

import com.example.core.model.Movie
import com.example.core.repository.MovieListRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface GetMovieListUseCase {
    operator fun invoke(params: GetParams): Flow<Resource<List<Movie>>>
    data class GetParams(
        val forceFetchFromRemote: Boolean,
        val category: String,
        val page: Int
    )
}

class GetMovieListUseCaseImpl @Inject constructor(
    private val repository: MovieListRepository
) : GetMovieListUseCase {
    override fun invoke(params: GetMovieListUseCase.GetParams): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val movieList = repository.getMovieList(
                    params.forceFetchFromRemote, params.category, params.page
                )
                emit(
                    Resource.Success(
                        data = movieList.sortedByDescending { it.release_date }
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }catch (e:Exception){
                emit(Resource.Error(message = "Error ao carregar filmes"))
                emit(Resource.Loading(false))
                return@flow
            }

        }
    }


}