package com.project.sejasa.di

import android.app.Application
import android.content.Context
import com.project.sejasa.rx.SchedulersFacade
import com.project.sejasa.rx.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun providerScheduler(schedulersFacade: SchedulersFacade): SchedulersProvider

}