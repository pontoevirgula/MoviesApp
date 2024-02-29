package com.chslcompany.moviesapp.feature_movies.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

@Entity
data class MovieDb(
    val backdrop_path: String,
    val original_language: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,

    @PrimaryKey
    val id : Int,
    val category : String
){
    fun toMovie(category: String) : Movie =
        Movie(
            backdrop_path = backdrop_path,
            original_language = original_language,
            overview = overview,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            vote_average = vote_average,
            id = id,
            category = category,
        )
}
