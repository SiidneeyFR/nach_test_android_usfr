package com.example.nach_test.di.modules.fragment

import com.example.nach_test.ui.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MoviesFragmentProvider {
    @ContributesAndroidInjector(modules = [MoviesFragmentModule::class])
    abstract fun provideMoviesFragmentFactory(): MoviesFragment
}