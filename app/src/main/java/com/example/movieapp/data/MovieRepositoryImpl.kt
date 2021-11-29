package com.example.movieapp.data

import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MovieRepository {

    private lateinit var genres: List<Genre>
    private lateinit var movies: List<Movie>

    override suspend fun getGenreList(): List<Genre> {
        if (!this::genres.isInitialized) {
            genres = fetchMovieList().map(Movie::genres).flatten().distinct()
        }
        return genres
    }

    override suspend fun getMovieList(): List<Movie> {
        if (!this::movies.isInitialized) {
            movies = fetchMovieList()
        }
        return movies
    }

    override suspend fun getMovie(movieId: Int): Movie {
        if (!this::movies.isInitialized) {
            movies = fetchMovieList()
        }
        return movies.find { movie -> movie.id == movieId } ?: throw IllegalArgumentException()
    }

    private suspend fun fetchMovieList(): List<Movie> =
        movieDataSource.getMovieList()
}