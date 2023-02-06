package com.myapp.mobilecomputing.data.repository

import com.myapp.mobilecomputing.data.entity.User
import com.myapp.mobilecomputing.data.room.UserDao
import kotlinx.coroutines.flow.Flow

/**
 * A data repository for [User] instances
 */
class UserRepository(
    private val userDao: UserDao
) {
    fun users(): Flow<List<User>> = userDao.users()
    fun getUserWithUsername(username : String): User? = userDao.getUserWithUsername(username)
    suspend fun deleteUser(user : User) = userDao.delete(user)

    /**
     * Add a new [User] to the payment store
     */
    suspend fun addUser(user : User) = userDao.insert(user)
}