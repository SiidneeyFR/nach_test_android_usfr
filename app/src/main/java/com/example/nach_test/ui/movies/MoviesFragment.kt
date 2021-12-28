package com.example.nach_test.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.movies.interactor.GetMoviesPopularInteractor
import com.example.domain.movies.model.Movie
import com.example.nach_test.BuildConfig
import com.example.nach_test.R
import com.example.nach_test.databinding.FragmentMoviesBinding
import com.example.nach_test.presenation.movies.MoviesContract
import com.example.nach_test.presenation.movies.MoviesPresenter
import com.example.nach_test.ui.utils.NetworkUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MoviesFragment : Fragment(), MoviesContract.View, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal
    lateinit var moviesPresenter: MoviesContract.Presenter

    private lateinit var bindig: FragmentMoviesBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        bindig = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return bindig.root
    }

    //obtener peliculas populares
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val haveInternet = context?.let { NetworkUtil().haveInternet(it) } ?: false
        moviesPresenter.getMoviesPopular(
            GetMoviesPopularInteractor.Params(BuildConfig.API_KEY, haveInternet)
        )
    }

    //mostrar películas o mostrar mensaje de vacio
    override fun onMoviesPopular(movies: List<Movie>) {
        if(movies.isNotEmpty()) {
            createMoviesList(movies)
        } else {
            showEmpty()
        }
    }

    //crear listado en un RecyclerView
    private fun createMoviesList(movies: List<Movie>) {
        val advanceProductAdapter = MovieAdpater(movies)
        val gridLayoutManager = GridLayoutManager(context, 1)

        bindig.fragmentMoviesRvMovies.apply {
            layoutManager = gridLayoutManager
            adapter = advanceProductAdapter
        }

        bindig.fragmentMoviesRvMovies.visibility = View.VISIBLE

    }

    // mostra texto de no hay peliculas
    private fun showEmpty() {
        bindig.fragmentMoviesTvEmpty.visibility = View.VISIBLE
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =  fragmentInjector

    override fun setPresenter(presenter: MoviesPresenter) {
        this.moviesPresenter = presenter
    }
}