package com.example.nach_test.data.movies.repository

import com.example.nach_test.data.movies.model.MovieEntity
import io.reactivex.Single

interface MoviesCache {
    fun saveMovies(movies: List<MovieEntity>): Single<String?>
    fun getMovies(): Single<List<MovieEntity>>
}