package com.example.nach_test.cache.movies.mapper

import com.example.nach_test.cache.CacheMapper
import com.example.nach_test.cache.movies.model.CacheMovie
import com.example.nach_test.data.movies.model.MovieEntity
import javax.inject.Inject

class CacheMovieMapper @Inject constructor() : CacheMapper<CacheMovie, MovieEntity> {

    override fun mapFromCached(cacheMovie: CacheMovie): MovieEntity =
        MovieEntity(
            cacheMovie.id,
            cacheMovie.adult,
            cacheMovie.backdropPath,
            cacheMovie.original_language,
            cacheMovie.original_title,
            cacheMovie.overview,
            cacheMovie.popularity,
            cacheMovie.poster_path,
            cacheMovie.release_date,
            cacheMovie.title,
            cacheMovie.video,
            cacheMovie.vote_average,
            cacheMovie.vote_count
        )

    override fun mapToCached(movieEntity: MovieEntity): CacheMovie =
        CacheMovie(
            movieEntity.id,
            movieEntity.adult,
            movieEntity.backdropPath,
            movieEntity.originalLanguage,
            movieEntity.originalTitle,
            movieEntity.overview,
            movieEntity.popularity,
            movieEntity.posterPath,
            movieEntity.releaseDate,
            movieEntity.title,
            movieEntity.video,
            movieEntity.voteAverage,
            movieEntity.voteCount
        )
}