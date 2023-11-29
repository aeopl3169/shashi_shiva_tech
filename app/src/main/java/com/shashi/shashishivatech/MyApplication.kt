package com.shashi.shashishivatech

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.shashi.shashishivatech.utils.NetworkConnectivityChecker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initNetworkConnectivityChecker()
    }

    private fun initNetworkConnectivityChecker() {
        NetworkConnectivityChecker.init(this.applicationContext)
    }

}



