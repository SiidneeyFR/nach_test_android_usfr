package com.example.nach_test.presenation.main

import com.example.domain.locations.model.Location
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(private val view: MainContract.View) : MainContract.Presenter {

    init {
        view.setPresenter(this)
    }
}