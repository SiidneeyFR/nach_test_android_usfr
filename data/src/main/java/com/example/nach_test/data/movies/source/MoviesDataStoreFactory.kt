package com.example.nach_test.data.movies.source

import com.example.nach_test.data.movies.repository.MoviesDataStore
import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(private val moviesRemoteDataStore: MoviesRemoteDataStore) {

    fun retrieveDataStore(): MoviesDataStore {
        return retrieveRemoteDataStore()
    }

    fun retrieveRemoteDataStore(): MoviesDataStore {
        return moviesRemoteDataStore
    }
}