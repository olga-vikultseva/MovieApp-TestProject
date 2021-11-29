package com.example.movieapp.data.network

import com.example.movieapp.data.network.model.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieApiService {

    @GET("sequeniatesttask/films.json")
    suspend fun getMovieList(): MoviesResponse

    companion object {

        operator fun invoke(): MovieApiService {

            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApiService::class.java)
        }

        private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com/"
    }
}
