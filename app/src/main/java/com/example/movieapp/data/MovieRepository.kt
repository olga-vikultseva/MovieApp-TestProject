package com.example.movieapp.data

import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun getGenreList(): List<Genre>
    suspend fun getMovieList(): List<Movie>
    suspend fun getMovie(movieId: Int): Movie
}