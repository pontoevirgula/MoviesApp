package com.chslcompany.moviesapp.feature_movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [MovieDb::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase(){
    abstract val movieDao : MovieDao
}