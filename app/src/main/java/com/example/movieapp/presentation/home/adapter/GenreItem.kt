package com.example.movieapp.presentation.home.adapter

data class GenreItem(
    val id: String,
    val name: String,
    val isSelected: Boolean
) : DisplayableItem
