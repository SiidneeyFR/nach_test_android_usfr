package com.example.domain.movies.interactor

import com.example.domain.executor.PostExecutionThread
import com.example.domain.executor.ThreadExecutor
import com.example.domain.interactor.SingleUseCase
import com.example.domain.movies.model.Movie
import com.example.domain.movies.repository.MoviesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesPopularInteractor @Inject constructor(
    private val moviesRepository: MoviesRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Movie>, GetMoviesPopularInteractor.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<Movie>> {
        return moviesRepository.getMoviesPopular(params.apiKey)
    }

    data class Params(
        val apiKey: String
    )
}