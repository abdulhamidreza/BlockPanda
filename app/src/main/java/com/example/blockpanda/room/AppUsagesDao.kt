package com.example.blockpanda.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsagesDao {

    @Query("SELECT * FROM AppUsages")
    fun getAllAppUsages(): Flow<MutableList<Usages>> //Flow to observer db changes in real time

    @Query("SELECT * FROM AppUsages WHERE pkgName = :pkgName LIMIT 1")
    fun getAppUsages(pkgName: String): Usages

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppUsages(usages: Usages)

    @Update
    suspend fun updateAppUsages(usages: Usages)

    @Delete
    suspend fun deleteAppUsages(usages: Usages)

}