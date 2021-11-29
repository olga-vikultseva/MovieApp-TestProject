package com.example.movieapp.domain.model

sealed class HomeState {
    object Loading : HomeState()

    data class Content(
        val selectedGenreId: String?,
        val genres: List<Genre>,
        val movies: List<Movie>
    ) : HomeState()

    data class Error(
        val message: String
    ) : HomeState()
}
