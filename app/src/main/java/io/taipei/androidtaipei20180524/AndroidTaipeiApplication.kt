package io.taipei.androidtaipei20180524

import android.app.Application

class AndroidTaipeiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}