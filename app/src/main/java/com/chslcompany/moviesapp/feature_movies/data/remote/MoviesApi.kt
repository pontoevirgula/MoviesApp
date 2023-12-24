package com.chslcompany.moviesapp.feature_movies.data.remote

import com.chslcompany.moviesapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : MovieListDTO

}