package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.domain.StringErrorHandler
import com.example.movieapp.util.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideErrorHandler(stringProvider: StringProvider): StringErrorHandler =
        StringErrorHandler(stringProvider)

    @Provides
    @Singleton
    fun provideStringProvider(@ApplicationContext context: Context): StringProvider =
        StringProvider(context)
}