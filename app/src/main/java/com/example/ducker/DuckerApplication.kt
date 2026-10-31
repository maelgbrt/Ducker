package com.example.ducker

import android.app.Application
import com.example.ducker.local.DuckerDatabaseHolder
import com.example.ducker.remote.FoodApi
import com.example.ducker.repository.DuckerRepository

class DuckerApplication : Application() {

    val repository: DuckerRepository by lazy {
        DuckerRepository(
            api = FoodApi,
            db = DuckerDatabaseHolder.database
        )
    }

    override fun onCreate() {
        super.onCreate()
        DuckerDatabaseHolder.initialize(this)
    }
}