package com.chslcompany.moviesapp.feature_movies.data.di

import com.example.core.usecase.moviedetailusecase.GetDetailMovieUseCase
import com.example.core.usecase.moviedetailusecase.GetDetailMovieUseCaseImpl
import com.example.core.usecase.movielistusecase.GetMovieListUseCase
import com.example.core.usecase.movielistusecase.GetMovieListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetMovieUseCase(
        useCaseImpl: GetMovieListUseCaseImpl
    ) : GetMovieListUseCase

    @Binds
    @Singleton
    abstract fun bindGetMovieDetailUseCase(
        useCaseImpl: GetDetailMovieUseCaseImpl
    ) : GetDetailMovieUseCase
}