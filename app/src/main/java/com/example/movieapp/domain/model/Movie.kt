package com.example.movieapp.domain.model

data class Movie(
    val id: Int,
    val localizedTitle: String,
    val title: String,
    val year: Int,
    val rating: Float,
    val imageUrl: String?,
    val description: String?,
    val genres: List<Genre>
)