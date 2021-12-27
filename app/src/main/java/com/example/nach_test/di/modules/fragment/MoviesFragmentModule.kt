package com.example.nach_test.di.modules.fragment

import com.example.domain.movies.interactor.GetMoviesPopularInteractor
import com.example.domain.movies.interactor.SaveMoviesPopularInteractor
import com.example.nach_test.presenation.movies.MoviesContract
import com.example.nach_test.presenation.movies.MoviesPresenter
import com.example.nach_test.ui.movies.MoviesFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MoviesFragmentModule{
    @Binds
    abstract fun bindMoviesFragmentView(fragment: MoviesFragment): MoviesContract.View

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun bindMoviesPresenter(
            view: MoviesContract.View,
            getMoviesPopularInteractor: GetMoviesPopularInteractor,
            saveMoviesPopularInteractor: SaveMoviesPopularInteractor): MoviesContract.Presenter =
                MoviesPresenter(view, getMoviesPopularInteractor, saveMoviesPopularInteractor)
    }
}