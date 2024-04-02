package com.chslcompany.moviesapp.feature_movies.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDao
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDb


@Database(
    entities = [MovieDb::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase(){
    abstract val movieDao : MovieDao
}