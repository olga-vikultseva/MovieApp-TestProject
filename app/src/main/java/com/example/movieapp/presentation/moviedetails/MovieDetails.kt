package com.example.movieapp.presentation.moviedetails

data class MovieDetails(
    val localizedTitle: String,
    val title: String,
    val year: String,
    val rating: String,
    val imageUrl: String?,
    val description: String?
)
