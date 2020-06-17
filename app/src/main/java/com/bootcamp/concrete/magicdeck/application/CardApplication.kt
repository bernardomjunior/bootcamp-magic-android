package com.bootcamp.concrete.magicdeck.application

import android.app.Application
import com.bootcamp.concrete.magicdeck.module.cardModule
import org.koin.core.context.startKoin


class CardApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(cardModule)

        }
    }
}