package com.example.nach_test.cache.movies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nach_test.cache.database.constants.DataBaseConstants
import com.example.nach_test.cache.movies.model.CacheMovie

@Dao
interface CacheMovieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(entities: CacheMovie)

    @Query("SELECT * FROM ${DataBaseConstants.MovieItem.TABLE_NAME}")
    fun getMovies(): List<CacheMovie>

}