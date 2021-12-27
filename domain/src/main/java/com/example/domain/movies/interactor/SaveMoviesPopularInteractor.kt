package com.example.domain.movies.interactor

import com.example.domain.executor.PostExecutionThread
import com.example.domain.executor.ThreadExecutor
import com.example.domain.interactor.SingleUseCase
import com.example.domain.movies.model.Movie
import com.example.domain.movies.repository.MoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class SaveMoviesPopularInteractor @Inject constructor(
    private val moviesRepository: MoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<String?, SaveMoviesPopularInteractor.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<String?> {
        return moviesRepository.saveMoviesPopular(params.movies)
    }

    data class Params(
        val movies: List<Movie>
    )
}