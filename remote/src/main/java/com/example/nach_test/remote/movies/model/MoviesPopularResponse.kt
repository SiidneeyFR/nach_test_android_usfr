package com.example.nach_test.remote.movies.model

import com.google.gson.annotations.SerializedName

data class MoviesPopularResponse (
    val page: Int,
    @SerializedName("results")
    val moviesPopular: List<MovieRemote>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)