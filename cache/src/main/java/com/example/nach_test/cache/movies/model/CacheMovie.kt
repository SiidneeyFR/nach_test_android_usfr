package com.example.nach_test.cache.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nach_test.cache.database.constants.DataBaseConstants

@Entity(tableName = DataBaseConstants.MovieItem.TABLE_NAME)
data class CacheMovie (
    @PrimaryKey
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_adult)
    val adult: Boolean,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_backdropPath)
    val backdropPath: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_original_language)
    val original_language: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_original_title)
    val original_title: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_overview)
    val overview: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_popularity)
    val popularity: Double,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_poster_path)
    val poster_path: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_release_date)
    val release_date: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_title)
    val title: String,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_video)
    val video: Boolean,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_vote_average)
    val vote_average: Float,
    @ColumnInfo(name = DataBaseConstants.MovieItem.COLUMN_NAME_vote_count)
    val vote_count: Int
)