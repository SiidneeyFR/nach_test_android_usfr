package com.example.nach_test.data.movies.source

import com.example.domain.movies.model.Movie
import com.example.nach_test.data.movies.model.MovieEntity
import com.example.nach_test.data.movies.repository.MoviesDataStore
import com.example.nach_test.data.movies.repository.MoviesRemote
import io.reactivex.Single
import javax.inject.Inject

class MoviesRemoteDataStore @Inject constructor(private val moviesRemote: MoviesRemote) : MoviesDataStore {

    override fun getMoviesPopular(apiKey: String): Single<List<Movie>> =
        moviesRemote.getMoviesPopular(apiKey)

    override fun getMoviesPopularCache(apiKey: String): Single<List<MovieEntity>> {
        throw UnsupportedOperationException()
    }

    override fun saveMoviesPopula(movies: List<MovieEntity>): Single<String?> {
        throw UnsupportedOperationException()
    }
}