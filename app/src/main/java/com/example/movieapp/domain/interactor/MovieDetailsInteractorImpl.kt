package com.example.movieapp.domain.interactor

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.StringErrorHandler
import com.example.movieapp.domain.model.MovieDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MovieDetailsInteractorImpl @Inject constructor(
    private val errorHandler: StringErrorHandler,
    private val movieRepository: MovieRepository
) : MovieDetailsInteractor {

    private val _movieDetailsStateFlow = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    override val movieDetailsStateFlow = _movieDetailsStateFlow as StateFlow<MovieDetailsState>

    override suspend fun requestMovieDetails(movieId: Int) {
        _movieDetailsStateFlow.value = handleState {
            val movie = movieRepository.getMovie(movieId)
            MovieDetailsState.Content(movie)
        }
    }

    private inline fun handleState(block: () -> MovieDetailsState.Content): MovieDetailsState =
        try {
            block()
        } catch (e: Throwable) {
            MovieDetailsState.Error(errorHandler.handle(e))
        }
}
