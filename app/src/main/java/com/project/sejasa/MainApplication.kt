package com.project.sejasa

import android.content.Context
import com.project.sejasa.di.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import androidx.multidex.MultiDex

class MainApplication : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        return appComponent
    }

}