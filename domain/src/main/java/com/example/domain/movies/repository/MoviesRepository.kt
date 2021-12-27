package com.example.domain.movies.repository

import com.example.domain.movies.model.Movie
import io.reactivex.Single

interface MoviesRepository {
    fun getMoviesPopular(apiKey: String, haveInternet: Boolean): Single<List<Movie>>
    fun saveMoviesPopular(movies: List<Movie>): Single<String?>
}