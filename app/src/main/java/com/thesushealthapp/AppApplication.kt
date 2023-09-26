package com.thesushealthapp

import android.app.Application
import android.util.Log
import com.polidea.rxandroidble3.LogConstants
import com.polidea.rxandroidble3.LogOptions
import com.polidea.rxandroidble3.RxBleClient


class AppApplication : Application() {

    companion object {
        lateinit var rxBleClient: RxBleClient
            private set
    }

    override fun onCreate() {
        super.onCreate()
        rxBleClient = RxBleClient.create(this)
        RxBleClient.updateLogOptions(
            LogOptions.Builder()
                .setLogLevel(LogConstants.INFO)
                .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
                .setUuidsLogSetting(LogConstants.UUIDS_FULL)
                .setShouldLogAttributeValues(true)
                .build()
        )
        //TODO remove this and set the correct Firebase database key here
        Log.d(
            "AppApplication",
            "BuildConfig.BUILD_TYPE: ${BuildConfig.BUILD_TYPE}")
        Log.d(
            "AppApplication",
            "BuildConfig.DEBUG: ${BuildConfig.DEBUG}")
    }
}
