package com.example.ducker.data.local

import android.content.Context
import androidx.room.Room

object DuckerDatabaseHolder {
    lateinit var database: DuckerDatabase
        private set

    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            DuckerDatabase::class.java,
            "ducker_db"
        )
            .build()
    }
}