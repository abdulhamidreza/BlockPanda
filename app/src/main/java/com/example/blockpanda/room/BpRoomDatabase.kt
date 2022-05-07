package com.example.blockpanda.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [
        Usages::class
    ],
    version = 1,
    exportSchema = false
) //exportSchema = false true In case of Migration
public abstract class BpRoomDatabase : RoomDatabase() {

    abstract fun userDao(): AppUsagesDao

    companion object {

        @Volatile    //This will guarantee visibility of changes for other threads as soon as the value is changed
        private var INSTANCE: BpRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BpRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BpRoomDatabase::class.java,
                    "panda"
                ).build()

                INSTANCE = instance

                // return instance
                instance
            }
        }
    }

}