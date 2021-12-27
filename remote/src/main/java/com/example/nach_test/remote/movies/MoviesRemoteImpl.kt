package com.example.nach_test.remote.movies

import com.example.domain.movies.model.Movie
import com.example.nach_test.data.movies.repository.MoviesRemote
import com.example.nach_test.remote.ServicesDirectory
import com.example.nach_test.remote.movies.mapper.MovieRemoteMapper
import io.reactivex.Single

class MoviesRemoteImpl ( private val service: ServicesDirectory,
    private val mapper: MovieRemoteMapper) : MoviesRemote {

    override fun getMoviesPopular(apiKey: String): Single<List<Movie>> =
        service.getMoviesPopular(apiKey).map {
            mapper.mapFromRemote(it)
        }
}