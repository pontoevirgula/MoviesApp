package com.chslcompany.moviesapp.feature_movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chslcompany.moviesapp.feature_movies.domain.model.Movie

@Entity
data class MovieDb(
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
    fun toMovie(category: String) : Movie =
        Movie(
            backdrop_path = backdrop_path,
            original_language = original_language,
            overview = overview,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            vote_average = vote_average,
            popularity = popularity,
            vote_count = vote_count,
            video = video,
            id = id,
            adult = adult,
            original_title = original_title,

            category = category,

            genre_ids = try {
                genre_ids.split(",").map { it.toInt() }
            } catch (e: Exception) {
                listOf(-1, -2)
            }
        )
}
