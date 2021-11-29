package com.example.movieapp.data.datasource

import com.example.movieapp.domain.model.Movie

interface MovieDataSource {
    suspend fun getMovieList(): List<Movie>
}