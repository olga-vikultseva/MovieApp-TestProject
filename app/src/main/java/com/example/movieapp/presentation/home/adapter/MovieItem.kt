package com.example.movieapp.presentation.home.adapter

data class MovieItem(
    val id: Int,
    val title: String,
    val imageUrl: String?
) : DisplayableItem
