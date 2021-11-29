package com.example.movieapp.di

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.MovieRepositoryImpl
import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindMovieRepository(implementation: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun bindMovieDataSource(implementation: MovieDataSourceImpl): MovieDataSource
}