package com.example.testing

import com.example.core.model.Movie
import com.example.core.util.Resource
import kotlinx.coroutines.flow.flow

class MovieFactory {

    fun create(movie: Movie) =
        when (movie) {
            Movie.MovieSuccess -> getDataMock
        }


    sealed class Movie {
        object MovieSuccess : Movie()
    }


    private val getDataMock =
        flow {
            emit(
                Resource.Success(
                    listOf(
                        Movie(
                            backdrop_path = "",
                            id = 1,
                            original_language = "pt",
                            "",
                            "",
                            "20/02/2024",
                            "batman",
                            4.0,
                            "popular"
                        )
                    )
                )
            )
        }
}