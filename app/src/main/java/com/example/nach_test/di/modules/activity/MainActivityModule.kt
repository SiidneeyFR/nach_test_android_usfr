package com.example.nach_test.di.modules.activity

import com.example.nach_test.MainActivity
import com.example.nach_test.di.scope.PerActivity
import com.example.nach_test.presenation.main.MainContract
import com.example.nach_test.presenation.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @PerActivity
    @Provides
    fun bindMainView(activity: MainActivity): MainContract.View {
        return activity
    }

    @PerActivity
    @Provides
    fun bindMainPresenter(
        view: MainContract.View): MainContract.Presenter {
        return MainPresenter(view)
    }
}