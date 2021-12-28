package com.example.nach_test.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nach_test.cache.database.constants.DataBaseConstants
import com.example.nach_test.cache.movies.dao.CacheMovieItemDao
import com.example.nach_test.cache.movies.model.CacheMovie

@Database(
    entities = [CacheMovie::class],
    version = 1
)

abstract class DataBaseApp : RoomDatabase() {

    abstract fun cacheMovieItemDao(): CacheMovieItemDao

    companion object {

        private var INSTANCE: DataBaseApp? = null

        //crear base datos
        fun createDatabase(context: Context): DataBaseApp {
            val builder = Room.databaseBuilder(context, DataBaseApp::class.java, DataBaseConstants.DATA_BASE_NAME)
            if (INSTANCE == null) {
                INSTANCE = builder.build()
            }
            return INSTANCE as DataBaseApp
        }
    }
}