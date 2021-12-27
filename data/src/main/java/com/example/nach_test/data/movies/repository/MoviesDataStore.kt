package com.example.nach_test.data.movies.repository

import com.example.domain.movies.model.Movie
import io.reactivex.Single

interface MoviesDataStore {
    fun getMoviesPopular(apiKey: String): Single<List<Movie>>
}