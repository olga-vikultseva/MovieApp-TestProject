package com.example.movieapp.domain.model

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()

    data class Content(
        val movie: Movie
    ) : MovieDetailsState()

    data class Error(
        val message: String
    ) : MovieDetailsState()
}