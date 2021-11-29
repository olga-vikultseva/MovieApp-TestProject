package com.example.movieapp.presentation.moviedetails

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MovieDetailsView : MvpView {

    @AddToEndSingle
    fun showLoading(isLoading: Boolean)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_data")
    fun showData(movieDetails: MovieDetails)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_data")
    fun hideData()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_error")
    fun showError(message: String)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_error")
    fun hideError()
}