package com.chslcompany.moviesapp.feature_movies.domain.model

data class Movie(
    val backdrop_path: String,
    val id: Int,
    val original_language: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,

    val category: String,
)
