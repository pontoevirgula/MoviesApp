package com.chslcompany.moviesapp.feature_movies.data.di

import android.app.Application
import androidx.room.Room
import com.chslcompany.moviesapp.feature_movies.data.local.MovieDao
import com.chslcompany.moviesapp.feature_movies.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesMovieDatabase(
        app : Application
    ) : MovieDatabase =
        Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_db"
        ).build()

    @Provides
    fun providesMovieDAO(
        movieDatabase: MovieDatabase
    ) : MovieDao =
        movieDatabase.movieDao


}