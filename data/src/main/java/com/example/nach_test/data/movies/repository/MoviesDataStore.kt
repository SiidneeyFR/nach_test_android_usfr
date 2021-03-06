package com.example.nach_test.data.movies.repository

import com.example.domain.movies.model.Movie
import com.example.nach_test.data.movies.model.MovieEntity
import io.reactivex.Single

interface MoviesDataStore {
    fun getMoviesPopular(apiKey: String): Single<List<Movie>>
    fun getMoviesPopularCache(apiKey: String): Single<List<MovieEntity>>
    fun saveMoviesPopula(movies: List<MovieEntity>): Single<String?>
}