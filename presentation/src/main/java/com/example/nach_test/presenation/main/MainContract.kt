package com.example.nach_test.presenation.main

import com.example.domain.locations.model.Location

interface MainContract {
    interface View {
        fun setPresenter(presenter: MainPresenter)
    }

    interface Presenter {
    }
}