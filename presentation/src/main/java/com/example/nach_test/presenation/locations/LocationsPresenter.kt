package com.example.nach_test.presenation.locations

import javax.inject.Inject

class LocationsPresenter @Inject constructor(private val view: LocationsContract.View) :
    LocationsContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getLocations() {
        view.onGetLocations(emptyList())
    }
}