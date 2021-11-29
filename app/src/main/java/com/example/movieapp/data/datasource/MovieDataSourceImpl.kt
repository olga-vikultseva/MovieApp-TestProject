package com.example.movieapp.data.datasource

import com.example.movieapp.data.network.MovieApiService
import com.example.movieapp.data.network.model.MovieApiModel
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieDataSource {

    override suspend fun getMovieList(): List<Movie> =
        movieApiService.getMovieList().movies.map(::movieMapper)

    private fun movieMapper(movieApiModel: MovieApiModel): Movie =
        Movie(
            id = movieApiModel.id,
            localizedTitle = movieApiModel.localizedName,
            title = movieApiModel.name,
            year = movieApiModel.year,
            rating = movieApiModel.rating,
            imageUrl = movieApiModel.imageUrl,
            description = movieApiModel.description,
            genres = movieApiModel.genres.map(::genreMapper)
        )

    private fun genreMapper(genreApiModel: String): Genre =
        Genre(
            id = genreApiModel,
            name = genreApiModel
        )
}