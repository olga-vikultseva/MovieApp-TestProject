package com.example.movieapp.domain.interactor

import com.example.movieapp.domain.model.HomeState
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    val homeStateFlow: Flow<HomeState>
    suspend fun toggleGenre(genreId: String)
    suspend fun requestHomeState()
}
