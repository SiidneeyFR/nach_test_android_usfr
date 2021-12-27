package com.example.nach_test.cache.database.constants

object DataBaseConstants {
    const val DATA_BASE_NAME = "com.nach_test.db"

    object MovieItem {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_adult = "adult"
        const val COLUMN_NAME_backdropPath = "backdrop_path"
        const val COLUMN_NAME_original_language = "original_language"
        const val COLUMN_NAME_original_title = "original_title"
        const val COLUMN_NAME_overview = "overview"
        const val COLUMN_NAME_popularity = "popularity"
        const val COLUMN_NAME_poster_path = "poster_path"
        const val COLUMN_NAME_release_date = "release_date"
        const val COLUMN_NAME_title = "title"
        const val COLUMN_NAME_video = "video"
        const val COLUMN_NAME_vote_average = "vote_average"
        const val COLUMN_NAME_vote_count = "vote_count"
    }
}