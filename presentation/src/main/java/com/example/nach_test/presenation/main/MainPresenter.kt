package com.example.nach_test.presenation.main

import com.example.domain.locations.model.Location
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(private val view: MainContract.View) : MainContract.Presenter, CoroutineScope {
    private var job: Job = SupervisorJob()
    //private val currentDeviceLocation = CurrentDeviceLocation()

    private var currentLocation = Location(0.0, 0.0)
    private var currentLocations = listOf<Location>()

    init {
        view.setPresenter(this)
    }


    override fun getLocations() {
        currentLocations = listOf(currentLocation, currentLocation)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        job.cancel()
    }

    override fun saveLocation() {
    }
}