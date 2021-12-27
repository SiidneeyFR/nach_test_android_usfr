package com.example.nach_test.data.movies.source

import com.example.nach_test.data.movies.repository.MoviesDataStore
import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(private val moviesRemoteDataStore: MoviesRemoteDataStore,
    private val moviesCahceDataStore: MoviesCacheDataStore) {

    fun retrieveDataStore(): MoviesCacheDataStore {
        return moviesCahceDataStore
    }

    fun retrieveRemoteDataStore(): MoviesDataStore {
        return moviesRemoteDataStore
    }
}