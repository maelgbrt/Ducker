package com.example.ducker

import android.app.Application
import com.example.ducker.local.DuckerDatabaseHolder

class DuckerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DuckerDatabaseHolder.initialize(this)
    }
}