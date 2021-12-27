package com.example.nach_test.cache

import com.example.nach_test.cache.database.DataBaseApp
import com.example.nach_test.cache.movies.mapper.CacheMovieMapper
import com.example.nach_test.data.movies.model.MovieEntity
import com.example.nach_test.data.movies.repository.MoviesCache
import io.reactivex.Single
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor( val database: DataBaseApp, val mapper: CacheMovieMapper)
    : MoviesCache {

    override fun getMovies(): Single<List<MovieEntity>> {
        return Single.create { e ->
            val movies = database.cacheMovieItemDao()
                .getMovies()
                .map {
                    mapper.mapFromCached(it)
                }
            e.onSuccess(movies)
        }
    }

    override fun saveMovies(movies: List<MovieEntity>): Single<String?> {
        return Single.create { e ->
            movies.forEach {
                database
                    .cacheMovieItemDao()
                    .insertMovies(mapper.mapToCached(it))
            }
            e.onSuccess("")
        }
    }
}