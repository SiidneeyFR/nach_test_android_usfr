package com.example.nach_test.presenation.locations

import com.example.domain.locations.model.Location

interface LocationsContract {
    interface View {
        fun onGetLocations(locations: List<Location>)
        fun setPresenter(presenter: LocationsPresenter)
    }

    interface Presenter {
        fun getLocations()
    }
}