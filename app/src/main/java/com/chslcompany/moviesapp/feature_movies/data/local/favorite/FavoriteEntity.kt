package com.chslcompany.moviesapp.feature_movies.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

@Entity
data class FavoriteEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    @PrimaryKey
    val id : Int,
    val category : String
){
    fun toMovieFromFavoriteDb(category: String) : Movie =
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
