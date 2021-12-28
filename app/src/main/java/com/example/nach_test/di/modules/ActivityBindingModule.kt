package com.example.nach_test.di.modules

import com.example.nach_test.MainActivity
import com.example.nach_test.di.modules.activity.MainActivityModule
import com.example.nach_test.di.modules.fragment.LocationsFragmentProvider
import com.example.nach_test.di.modules.fragment.MoviesFragmentProvider
import com.example.nach_test.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [MoviesFragmentProvider::class, MainActivityModule::class,
        LocationsFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}