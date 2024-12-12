package com.example.newsify.viewmodel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class Newisfyapp : Application(){
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}