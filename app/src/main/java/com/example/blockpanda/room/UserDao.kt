package com.ezylaw.lawbook.room

import androidx.room.*
import com.example.blockpanda.room.Usages
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDetails")
    fun getUsers(): Flow<MutableList<Usages>> //Flow to observer db changes in real time

    @Query("SELECT * FROM UserDetails WHERE userId = :userId AND pwd = :pwd LIMIT 1")
    fun loginUsers(userId: Long, pwd: String): Usages

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: Usages)

    @Update
    suspend fun updateUser(user: Usages)

    @Delete
    suspend fun deleteUser(user: Usages)

}