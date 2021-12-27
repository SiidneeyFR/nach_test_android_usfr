package com.example.nach_test.data.movies.mapper

import com.example.domain.movies.model.Movie
import com.example.nach_test.data.movies.DataMapper
import com.example.nach_test.data.movies.model.MovieEntity
import javax.inject.Inject

class MovieDataMapper @Inject constructor() : DataMapper<MovieEntity, Movie> {

    override fun mapFromEntity(type: MovieEntity): Movie =
        Movie(
            type.adult,
            type.backdropPath,
            type.id,
            type.originalLanguage,
            type.originalTitle,
            type.overview,
            type.popularity,
            type.posterPath,
            type.releaseDate,
            type.title,
            type.video,
            type.voteAverage,
            type.voteCount
        )

    override fun mapToEntity(type: Movie): MovieEntity =
        MovieEntity(
            type.id,
            type.adult,
            type.backdropPath,
            type.originalLanguage,
            type.originalTitle,
            type.overview,
            type.popularity,
            type.posterPath,
            type.releaseDate,
            type.title,
            type.video,
            type.voteAverage,
            type.voteCount
        )
}