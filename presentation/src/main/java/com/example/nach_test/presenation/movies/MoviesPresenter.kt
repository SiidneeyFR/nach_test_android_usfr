package com.example.nach_test.presenation.movies

import com.example.domain.movies.interactor.GetMoviesPopularInteractor
import com.example.domain.movies.interactor.SaveMoviesPopularInteractor
import com.example.domain.movies.model.Movie
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MoviesPresenter @Inject constructor(private val view: MoviesContract.View,
    private val getMoviesPopularInteractor: GetMoviesPopularInteractor,
    private val saveMoviesPopularInteractor: SaveMoviesPopularInteractor
) : MoviesContract.Presenter {

    init {
        view.setPresenter(this)
    }

    //acción para obtener peliculas
    override fun getMoviesPopular(params: GetMoviesPopularInteractor.Params) {
        getMoviesPopularInteractor.execute(object : DisposableSingleObserver<List<Movie>>(){
            override fun onSuccess(movies: List<Movie>) {
                saveMovies(SaveMoviesPopularInteractor.Params(movies))
                view.onMoviesPopular(movies)
            }

            override fun onError(e: Throwable) {
                view.onMoviesPopular(emptyList())
            }
        }, params)
    }

    //acción para guardar peliculas en cache
    private fun saveMovies(params: SaveMoviesPopularInteractor.Params) {
        saveMoviesPopularInteractor.execute(object : DisposableSingleObserver<String?>(){
            override fun onError(e: Throwable) {
            }

            override fun onSuccess(t: String) {
            }
        }, params)
    }
}