package com.example.nach_test.ui.movies

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.movies.model.Movie
import com.example.domain.movies.model.getUrlPoster
import com.example.nach_test.BuildConfig
import com.example.nach_test.R
import com.example.nach_test.databinding.ViewMovieBinding

class MovieAdpater(private val movies: List<Movie>):
    RecyclerView.Adapter<MovieAdpater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_movie, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    inner class ViewHolder(val binding: ViewMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(movie) {
                binding.viewMovieIvMovie.setImageURI(movie.getUrlPoster(BuildConfig.SERVER_IMAGE_URL))
                binding.viewMovieTvTitle.text = title
                binding.viewMovieTvDescription.text = overview
                binding.viewMovieTvOriginalTitle.text = Html.fromHtml(binding.root.resources.getString(R.string.view_movie_original_title, originalTitle))
                binding.viewMovieTvReleaseDate.text = Html.fromHtml(binding.root.resources.getString(R.string.view_movie_release_date, releaseDate))
                binding.viewMovieTvVoteAverage.text = Html.fromHtml(binding.root.resources.getString(R.string.view_movie_vote_average, voteAverage.toString()))
                binding.viewMovieTvVoteCount.text = Html.fromHtml(binding.root.resources.getString(R.string.view_movie_vote_count, voteCount.toString()))
            }
        }
    }
}