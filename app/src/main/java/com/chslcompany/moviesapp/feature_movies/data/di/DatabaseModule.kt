package com.chslcompany.moviesapp.feature_movies.data.di

import android.app.Application
import androidx.room.Room
import com.chslcompany.moviesapp.feature_movies.data.local.favorite.FavoriteDao
import com.chslcompany.moviesapp.feature_movies.data.local.favorite.FavoriteDatabase
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDao
import com.chslcompany.moviesapp.feature_movies.data.local.movie.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesMovieDatabase(
        app: Application
    ): MovieDatabase =
        Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_db"
        ).build()

    @Provides
    fun providesMovieDAO(
        movieDatabase: MovieDatabase
    ): MovieDao =
        movieDatabase.movieDao

    @Provides
    fun providesFavoriteMovieDatabase(
        app: Application
    ): FavoriteDatabase =
        Room.databaseBuilder(
            app,
            FavoriteDatabase::class.java,
            "favorite_db"
        ).build()

    @Provides
    fun providesFavoriteMovieDAO(
        favoriteDatabase: FavoriteDatabase
    ): FavoriteDao =
        favoriteDatabase.favoriteDao()


}