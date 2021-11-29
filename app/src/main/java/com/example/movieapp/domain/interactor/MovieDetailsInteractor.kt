package com.example.movieapp.domain.interactor

import com.example.movieapp.domain.model.MovieDetailsState
import kotlinx.coroutines.flow.Flow

interface MovieDetailsInteractor {
    val movieDetailsStateFlow: Flow<MovieDetailsState>
    suspend fun requestMovieDetails(movieId: Int)
}