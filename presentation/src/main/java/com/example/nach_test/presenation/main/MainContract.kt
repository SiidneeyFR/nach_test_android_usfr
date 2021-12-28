package com.example.nach_test.presenation.main

import com.example.domain.locations.model.Location

interface MainContract {
    interface View {
        fun showNotifcation(location: Location)
        fun setPresenter(presenter: MainPresenter)
    }

    interface Presenter {
        fun getLocations()
        fun saveLocation()
        fun onDestroy()
    }
}