package com.example.movieapp.presentation.moviedetails

import com.example.movieapp.R
import com.example.movieapp.domain.interactor.MovieDetailsInteractor
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetailsState
import com.example.movieapp.util.StringProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class MovieDetailsPresenter @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val movieDetailsInteractor: MovieDetailsInteractor,
    private val stringProvider: StringProvider
) : MvpPresenter<MovieDetailsView>() {

    init {
        presenterScope.launch {
            movieDetailsInteractor.movieDetailsStateFlow.collect { movieDetailsState ->
                when (movieDetailsState) {
                    MovieDetailsState.Loading -> {
                        viewState.hideData()
                        viewState.hideError()
                        viewState.showLoading(isLoading = true)
                    }
                    is MovieDetailsState.Content -> {
                        viewState.showLoading(isLoading = false)
                        viewState.hideError()
                        viewState.showData(convertToMovieDetails(movieDetailsState.movie))
                    }
                    is MovieDetailsState.Error -> {
                        viewState.showLoading(isLoading = false)
                        viewState.hideData()
                        viewState.showError(movieDetailsState.message)
                    }
                }
            }
        }

        presenterScope.launch {
            movieDetailsInteractor.requestMovieDetails(movieId)
        }
    }

    private fun convertToMovieDetails(movie: Movie): MovieDetails {
        return MovieDetails(
            localizedTitle = movie.localizedTitle,
            title = movie.title,
            year = stringProvider.getString(R.string.movie_year_placeholder, movie.year.toString()),
            rating = stringProvider.getString(R.string.movie_rating_placeholder, movie.rating.toString()),
            imageUrl = movie.imageUrl,
            description = movie.description
        )
    }

    @AssistedFactory
    interface MovieDetailsPresenterFactory {
        fun create(movieId: Int): MovieDetailsPresenter
    }
}