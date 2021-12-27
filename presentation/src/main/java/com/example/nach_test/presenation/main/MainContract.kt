package com.example.nach_test.presenation.main

import com.example.domain.locations.model.Location

interface MainContract {
    interface View {
        fun showNotifcation(location: Location)
    }

    interface Presenter {
        fun getLocations()
        fun onDestroy()
    }
}