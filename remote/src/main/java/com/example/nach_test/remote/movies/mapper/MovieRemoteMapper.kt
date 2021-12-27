package com.example.nach_test.remote.movies.mapper

import com.example.domain.movies.model.Movie
import com.example.nach_test.remote.RemoteMapper
import com.example.nach_test.remote.movies.model.MoviesPopularResponse
import javax.inject.Inject

class MovieRemoteMapper @Inject constructor() : RemoteMapper<MoviesPopularResponse, List<Movie>> {
    override fun mapFromRemote(remote: MoviesPopularResponse) =
        remote.moviesPopular.map {
            Movie(
                it.adult,
                it.backdropPath,
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
        }

}