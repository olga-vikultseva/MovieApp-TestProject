package com.example.movieapp.presentation.home

import com.example.movieapp.presentation.home.adapter.DisplayableItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface HomeView : MvpView {

    @AddToEndSingle
    fun showLoading(isLoading: Boolean)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_data")
    fun showData(items: List<DisplayableItem>)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_data")
    fun hideData()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_error")
    fun showError(message: String)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "show_hide_error")
    fun hideError()

    @OneExecution
    fun navigateToMovieDetails(movieId: Int)
}