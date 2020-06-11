package com.example.rozrywkafirebase.data.model

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieEntity>,
    @SerializedName("total_pages") val pages: Int
)