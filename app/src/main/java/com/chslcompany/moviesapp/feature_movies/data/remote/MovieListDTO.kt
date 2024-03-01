package com.chslcompany.moviesapp.feature_movies.data.remote

import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDb

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieDTO(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    fun toMovieDb(category : String): MovieDb =
        MovieDb(
            backdrop_path = backdrop_path ?: "",
            original_language = original_language ?: "",
            overview = overview ?: "",
            poster_path = poster_path ?: "",
            release_date = release_date ?: "",
            title = title ?: "",
            vote_average = vote_average ?: 0.0,
            id = id ?: -1,
            category = category,
        )
}
