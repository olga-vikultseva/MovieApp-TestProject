package com.example.movieapp.di

import com.example.movieapp.domain.interactor.HomeInteractor
import com.example.movieapp.domain.interactor.HomeInteractorImpl
import com.example.movieapp.domain.interactor.MovieDetailsInteractor
import com.example.movieapp.domain.interactor.MovieDetailsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface DomainModule {
    @Binds
    fun bindHomeInteractor(implementation: HomeInteractorImpl): HomeInteractor

    @Binds
    fun bindMovieDetailsInteractor(implementation: MovieDetailsInteractorImpl): MovieDetailsInteractor
}