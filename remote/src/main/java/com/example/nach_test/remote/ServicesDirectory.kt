package com.example.nach_test.remote

import com.example.nach_test.remote.movies.model.MoviesPopularResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicesDirectory {

    @GET("3/movie/popular")
    fun getMoviesPopular(@Query("api_key") apiKey: String, @Query("language") language: String = "es"): Single<MoviesPopularResponse>
}