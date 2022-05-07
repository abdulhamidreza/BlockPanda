package com.ezylaw.lawbook.room

import androidx.annotation.WorkerThread
import com.example.blockpanda.room.Usages
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val getAllUserList: Flow<MutableList<Usages>> = userDao.getUsers()

    fun loginUsers(userId: Long, pwd: String): Usages {
        return userDao.loginUsers(userId, pwd)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: Usages) {
        userDao.insertUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(user: Usages) {
        userDao.deleteUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: Usages) {
        userDao.updateUser(user)
    }

}