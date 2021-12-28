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

    //obtener peliculas si hay conexion de remoto o caso contrario de local
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

    // guadar peliculas en bd local
    override fun saveMoviesPopular(movies: List<Movie>): Single<String?> {
        val moviesEnt = movies.map { movieDataMapper.mapToEntity(it) }
        return factory.retrieveDataStore().saveMoviesPopula(moviesEnt)
    }
}