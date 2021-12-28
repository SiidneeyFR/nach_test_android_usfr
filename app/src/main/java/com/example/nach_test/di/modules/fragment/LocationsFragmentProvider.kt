package com.example.nach_test.di.modules.fragment

import com.example.nach_test.ui.locations.LocationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationsFragmentProvider {
    @ContributesAndroidInjector(modules = [LocationsFragmentModule::class])
    abstract fun provideLocationsFragmentFactory(): LocationsFragment
}