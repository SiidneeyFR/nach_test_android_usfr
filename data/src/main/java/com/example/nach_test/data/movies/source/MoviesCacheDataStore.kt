package com.example.nach_test.data.movies.source

import com.example.domain.movies.model.Movie
import com.example.nach_test.data.movies.model.MovieEntity
import com.example.nach_test.data.movies.repository.MoviesCache
import com.example.nach_test.data.movies.repository.MoviesDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheDataStore @Inject constructor(
    private val moviesCache: MoviesCache) : MoviesDataStore {

    override fun getMoviesPopular(apiKey: String): Single<List<Movie>> {
        throw UnsupportedOperationException()
    }

    override fun getMoviesPopularCache(apiKey: String): Single<List<MovieEntity>> =
        moviesCache.getMovies()

    override fun saveMoviesPopula(movies: List<MovieEntity>): Single<String?> =
        moviesCache.saveMovies(movies)

}