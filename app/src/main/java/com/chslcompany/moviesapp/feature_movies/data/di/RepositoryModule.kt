package com.chslcompany.moviesapp.feature_movies.data.di

import com.chslcompany.moviesapp.feature_movies.data.repository.MovieListRepositoryImpl
import com.example.core.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        repositoryImpl: MovieListRepositoryImpl
    ) : MovieListRepository
}