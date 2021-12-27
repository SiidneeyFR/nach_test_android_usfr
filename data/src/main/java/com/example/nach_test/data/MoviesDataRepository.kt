package com.example.nach_test.data

import com.example.domain.movies.model.Movie
import com.example.domain.movies.repository.MoviesRepository
import com.example.nach_test.data.movies.source.MoviesDataStoreFactory
import io.reactivex.Single

class MoviesDataRepository (
    private val factory: MoviesDataStoreFactory
) : MoviesRepository {

    override fun getMoviesPopular(apiKey: String): Single<List<Movie>> = factory.retrieveRemoteDataStore()
        .getMoviesPopular(apiKey)

}