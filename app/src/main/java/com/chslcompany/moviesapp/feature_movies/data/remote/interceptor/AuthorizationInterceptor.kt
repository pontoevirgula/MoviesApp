package com.chslcompany.moviesapp.feature_movies.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**CLASSE QUE SERIA USADA CASO API_KEY FOSSE ENVIADA VIA HEADER NA CHAMADA DA API */
class AuthorizationInterceptor(private val apiKey : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", apiKey)
            .url(request.url)
            .build()

        return chain.proceed(newRequest)
    }
}