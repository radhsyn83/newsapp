package com.radhsyn83.newsapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Log.e("RXJAVA ERROR", e.message.toString())
            } else {
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler?.uncaughtException(thread, e)
                }
            }
    }
}}