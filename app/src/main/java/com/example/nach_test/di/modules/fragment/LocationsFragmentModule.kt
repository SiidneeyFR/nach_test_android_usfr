package com.example.nach_test.di.modules.fragment

import com.example.nach_test.presenation.locations.LocationsContract
import com.example.nach_test.presenation.locations.LocationsPresenter
import com.example.nach_test.ui.locations.LocationsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LocationsFragmentModule{
    @Binds
    abstract fun bindLocationsFragmentView(fragment: LocationsFragment): LocationsContract.View

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun bindLocationsPresenter(view: LocationsContract.View ): LocationsContract.Presenter =
            LocationsPresenter(view)
    }
}