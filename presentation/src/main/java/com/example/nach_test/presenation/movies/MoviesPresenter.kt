package com.example.nach_test.presenation.movies

import com.example.domain.movies.interactor.GetMoviesPopularInteractor
import com.example.domain.movies.model.Movie
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MoviesPresenter @Inject constructor(private val view: MoviesContract.View,
                      private val getMoviesPopularInteractor: GetMoviesPopularInteractor
) : MoviesContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getMoviesPopular(params: GetMoviesPopularInteractor.Params) {
        getMoviesPopularInteractor.execute(object : DisposableSingleObserver<List<Movie>>(){
            override fun onSuccess(movies: List<Movie>) {
                view.onMoviesPopular(movies)
            }

            override fun onError(e: Throwable) {
                view.onMoviesPopular(emptyList())
            }
        }, params)
    }
}