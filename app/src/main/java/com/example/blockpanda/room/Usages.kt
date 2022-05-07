package com.example.blockpanda.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserDetails")
data class Usages(
    @PrimaryKey @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "pwd") val pwd: String,
    @ColumnInfo(name = "userType") val userType: String, //client , lawyer , admin
    @ColumnInfo(name = "first") val first: String,
    @ColumnInfo(name = "last") val last: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "dob") val dob: String,  //Cal
    @ColumnInfo(name = "gender") val gender: String, //
    @ColumnInfo(name = "totalExpYear") val expYear: Int,
    @ColumnInfo(name = "degree") val degree: String, //Multi selection
    @ColumnInfo(name = "barRegNo") val barRegNo: String,
    @ColumnInfo(name = "barName") val barName: String

)
