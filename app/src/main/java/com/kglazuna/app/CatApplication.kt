package com.kglazuna.app

import android.app.Application
import timber.log.Timber

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
