package com.chslcompany.moviesapp.feature_movies.data.remote

import com.chslcompany.moviesapp.feature_movies.data.local.MovieDb

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
            adult = adult ?: false,
            backdrop_path = backdrop_path ?: "",
            original_language = original_language ?: "",
            original_title = original_title ?: "",
            overview = overview ?: "",
            popularity = popularity ?: 0.0,
            poster_path = poster_path ?: "",
            release_date = release_date ?: "",
            title = title ?: "",
            video = video ?: false,
            vote_average = vote_average ?: 0.0,
            vote_count = vote_count ?: 0,
            id = id ?: -1,
            category = category,

            genre_ids = try {
                genre_ids?.joinToString(",") ?: "-1,-2"
            } catch (e: Exception) {
                "-1,-2"
            }
        )
}
