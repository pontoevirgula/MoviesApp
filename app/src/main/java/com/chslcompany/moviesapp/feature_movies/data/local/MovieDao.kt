package com.chslcompany.moviesapp.feature_movies.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieDbList: List<MovieDb>)

    @Query("SELECT * FROM MovieDb WHERE id = :id")
    suspend fun getMovieById(id : Int) : MovieDb

    @Query("SELECT * FROM MovieDb WHERE category = :category")
    suspend fun getMovieListByCategory(category: String): List<MovieDb>
}