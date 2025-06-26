package com.giraffe.tudeeapp

import android.app.Application
import com.giraffe.tudeeapp.di.dataModule
import com.giraffe.tudeeapp.di.serviceModule
import com.giraffe.tudeeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TudeeApp)
            modules(viewModelModule, serviceModule, dataModule)
        }
    }
}