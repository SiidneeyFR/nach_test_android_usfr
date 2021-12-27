package com.example.nach_test.data.movies

import com.example.domain.movies.model.Movie
import com.example.domain.movies.repository.MoviesRepository
import com.example.nach_test.data.movies.mapper.MovieDataMapper
import com.example.nach_test.data.movies.source.MoviesDataStoreFactory
import io.reactivex.Single

class MoviesDataRepository (
    private val factory: MoviesDataStoreFactory,
    private val movieDataMapper: MovieDataMapper
) : MoviesRepository {

    override fun getMoviesPopular(apiKey: String, haveInternet: Boolean): Single<List<Movie>> {
        return if (haveInternet) {
            factory.retrieveRemoteDataStore()
                .getMoviesPopular(apiKey)
        } else {
            factory.retrieveDataStore().getMoviesPopularCache(apiKey).map { moviesE ->
                moviesE.map { movieDataMapper.mapFromEntity(it) }
            }
        }
    }



    override fun saveMoviesPopular(movies: List<Movie>): Single<String?> {
        val moviesEnt = movies.map { movieDataMapper.mapToEntity(it) }
        return factory.retrieveDataStore().saveMoviesPopula(moviesEnt)
    }
}