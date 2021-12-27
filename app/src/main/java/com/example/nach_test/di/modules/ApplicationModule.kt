package com.example.nach_test.di.modules

import android.app.Application
import android.content.Context
import com.example.domain.executor.PostExecutionThread
import com.example.domain.executor.ThreadExecutor
import com.example.domain.movies.repository.MoviesRepository
import com.example.nach_test.BuildConfig
import com.example.nach_test.UIThread
import com.example.nach_test.data.MoviesDataRepository
import com.example.nach_test.data.movies.repository.MoviesRemote
import com.example.nach_test.data.movies.source.MoviesDataStoreFactory
import com.example.nach_test.dataq.executor.JobExecutor
import com.example.nach_test.remote.ServicesDirectory
import com.example.nach_test.remote.ServicesFactory
import com.example.nach_test.remote.movies.MoviesRemoteImpl
import com.example.nach_test.remote.movies.mapper.MovieRemoteMapper
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providesApplicationContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Singleton
    @Provides
    fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Singleton
    @Provides
    fun provideServicesDirectory(application: Application): ServicesDirectory =
        ServicesFactory().makeApiService(BuildConfig.SERVER_API_URL)

    @Singleton
    @Provides
    fun providesMissionMenuRepository(factory: MoviesDataStoreFactory): MoviesRepository {
        return MoviesDataRepository(factory)
    }

    @Singleton
    @Provides
    fun providesMoviesRemote(servicesDirectory: ServicesDirectory, movieRemoteMapper: MovieRemoteMapper): MoviesRemote {
        return MoviesRemoteImpl(servicesDirectory, movieRemoteMapper)
    }
}