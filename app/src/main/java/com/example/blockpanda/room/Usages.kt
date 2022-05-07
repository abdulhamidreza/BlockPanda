package com.example.blockpanda.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AppUsages")
data class Usages(
    @PrimaryKey @ColumnInfo(name = "pkgName") val pkgName: String,
    @ColumnInfo(name = "appName") val appName: String,
    @ColumnInfo(name = "isLock") val isLock: Boolean,
    @ColumnInfo(name = "isTimeLock") val isTimeLock: Boolean,
    @ColumnInfo(name = "isIntervalLock") val isIntervalLock: Boolean,
    @ColumnInfo(name = "isCountLock") val isCountLock: Boolean,
    @ColumnInfo(name = "isLocationLock") val isLocationLock: Boolean,
    @ColumnInfo(name = "lockTime") val lockTime: String,
    @ColumnInfo(name = "lockInterval") val lockInterval: String,
    @ColumnInfo(name = "lockCount") val lockCount: String,
    @ColumnInfo(name = "lockLocation") val lockLocation: String,
    @ColumnInfo(name = "isBayPass") val isBayPass: Boolean,
    @ColumnInfo(name = "bayPassTime") val bayPassTime: String,
    @ColumnInfo(name = "bayPassCount") val bayPassCount: String,
    @ColumnInfo(name = "inRemoteGroup") val inRemoteGroup: Boolean,
)
