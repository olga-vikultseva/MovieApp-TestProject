package com.example.movieapp.domain.interactor

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.StringErrorHandler
import com.example.movieapp.domain.model.HomeState
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeInteractorImpl @Inject constructor(
    private val errorHandler: StringErrorHandler,
    private val movieRepository: MovieRepository
) : HomeInteractor {

    private val _homeStateFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    override val homeStateFlow = _homeStateFlow as StateFlow<HomeState>

    override suspend fun toggleGenre(genreId: String) {
        val homeContent = (_homeStateFlow.value as? HomeState.Content) ?: return
        val selectedGenreId = genreId.takeIf { it != homeContent.selectedGenreId }
        updateState(selectedGenreId)
    }

    override suspend fun requestHomeState() {
        val currentContent = (_homeStateFlow.value as? HomeState.Content)
        updateState(currentContent?.selectedGenreId)
    }

    private suspend fun updateState(newSelectedGenreId: String?) {
        _homeStateFlow.value = handleState {
            HomeState.Content(
                selectedGenreId = newSelectedGenreId,
                genres = movieRepository.getGenreList(),
                movies = movieRepository.getMovieList()
                    .filterByGenre(newSelectedGenreId)
                    .sortedBy { it.localizedTitle }
            )
        }
    }

    private inline fun handleState(block: () -> HomeState.Content): HomeState =
        try {
            block()
        } catch (e: Throwable) {
            HomeState.Error(errorHandler.handle(e))
        }

    private fun List<Movie>.filterByGenre(genreId: String?): List<Movie> =
        if (genreId != null) {
            filter { movie -> movie.genres.any { it.id == genreId } }
        } else {
            this
        }
}