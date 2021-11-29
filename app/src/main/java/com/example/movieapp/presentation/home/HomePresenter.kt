package com.example.movieapp.presentation.home

import com.example.movieapp.R
import com.example.movieapp.domain.interactor.HomeInteractor
import com.example.movieapp.domain.model.HomeState
import com.example.movieapp.presentation.home.adapter.DisplayableItem
import com.example.movieapp.presentation.home.adapter.GenreItem
import com.example.movieapp.presentation.home.adapter.HeaderItem
import com.example.movieapp.presentation.home.adapter.MovieItem
import com.example.movieapp.util.StringProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val homeInteractor: HomeInteractor,
    private val stringProvider: StringProvider
) : MvpPresenter<HomeView>() {

    init {
        presenterScope.launch {
            homeInteractor.homeStateFlow.collect { homeState ->
                when (homeState) {
                    is HomeState.Loading -> {
                        viewState.hideData()
                        viewState.hideError()
                        viewState.showLoading(isLoading = true)
                    }
                    is HomeState.Content -> {
                        viewState.showLoading(isLoading = false)
                        viewState.hideError()
                        viewState.showData(convertToDisplayableItems(homeState))
                    }
                    is HomeState.Error -> {
                        viewState.showLoading(isLoading = false)
                        viewState.hideData()
                        viewState.showError(homeState.message)
                    }
                }
            }
        }

        presenterScope.launch {
            homeInteractor.requestHomeState()
        }
    }

    private fun convertToDisplayableItems(content: HomeState.Content): List<DisplayableItem> {

        val genreItems = content.genres.map { genre ->
            GenreItem(
                id = genre.id,
                name = genre.name.replaceFirstChar { char -> char.uppercaseChar() },
                isSelected = genre.id == content.selectedGenreId
            )
        }

        val movieItems = content.movies.map { movie ->
            MovieItem(
                id = movie.id,
                title = movie.localizedTitle,
                imageUrl = movie.imageUrl
            )
        }

        return mutableListOf<DisplayableItem>().apply {
            add(HeaderItem(stringProvider.getString(R.string.genre_section_header_text)))
            addAll(genreItems)
            add(HeaderItem(stringProvider.getString(R.string.movie_section_header_text)))
            addAll(movieItems)
        }
    }

    fun onGenreClicked(genreId: String) {
        presenterScope.launch {
            homeInteractor.toggleGenre(genreId)
        }
    }

    fun onMovieClicked(movieId: Int) {
        viewState.navigateToMovieDetails(movieId)
    }

    fun onTryAgainClicked() {
        presenterScope.launch {
            homeInteractor.requestHomeState()
        }
    }
}