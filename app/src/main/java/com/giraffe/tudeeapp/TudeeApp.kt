package com.giraffe.tudeeapp

import android.app.Application
import com.giraffe.tudeeapp.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TudeeApp)
            modules(dataModule)
        }
    }
}