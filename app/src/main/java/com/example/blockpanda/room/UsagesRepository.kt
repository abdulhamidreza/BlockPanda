package com.example.blockpanda.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UsagesRepository(private val appUsagesDao: AppUsagesDao) {

    val getAllAppUsages: Flow<MutableList<Usages>> = appUsagesDao.getAllAppUsages()

    fun getAppUsages(pkgName: String): Usages {
        return appUsagesDao.getAppUsages(pkgName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAppUsages(usages: Usages) {
        appUsagesDao.insertAppUsages(usages)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateAppUsages(usages: Usages) {
        appUsagesDao.updateAppUsages(usages)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAppUsages(usages: Usages) {
        appUsagesDao.deleteAppUsages(usages)
    }

}