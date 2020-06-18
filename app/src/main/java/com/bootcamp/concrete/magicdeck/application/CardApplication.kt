package com.bootcamp.concrete.magicdeck.application

import android.app.Application
import com.bootcamp.concrete.magicdeck.di.networkModule
import com.bootcamp.concrete.magicdeck.di.repositoryModule
import com.bootcamp.concrete.magicdeck.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class CardApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CardApplication)
            modules(repositoryModule, viewModelModule, networkModule)
        }
    }
}