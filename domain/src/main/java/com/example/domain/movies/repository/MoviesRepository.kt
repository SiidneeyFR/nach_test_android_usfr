package com.example.domain.movies.repository

import com.example.domain.movies.model.Movie
import io.reactivex.Single

interface MoviesRepository {
    fun getMoviesPopular(apiKey: String): Single<List<Movie>>
}