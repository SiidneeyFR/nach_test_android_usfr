package com.example.nach_test.presenation.movies

import com.example.domain.movies.interactor.GetMoviesPopularInteractor
import com.example.domain.movies.model.Movie

interface MoviesContract {
    interface View {
        fun onMoviesPopular(movies: List<Movie>)
        fun setPresenter(presenter: MoviesPresenter)
    }

    interface Presenter {
        fun getMoviesPopular(params: GetMoviesPopularInteractor.Params)
    }
}