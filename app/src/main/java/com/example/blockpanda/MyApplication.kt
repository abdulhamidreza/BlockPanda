package com.example.blockpanda

import android.app.Application
import com.example.blockpanda.room.BpRoomDatabase
import com.example.blockpanda.room.UsagesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { BpRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UsagesRepository(database.userDao()) }
}