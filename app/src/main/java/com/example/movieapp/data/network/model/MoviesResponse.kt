package com.example.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("films")
    val movies: List<MovieApiModel>
)