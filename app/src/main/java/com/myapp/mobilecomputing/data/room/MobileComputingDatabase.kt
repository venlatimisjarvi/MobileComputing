package com.myapp.mobilecomputing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapp.mobilecomputing.data.entity.User

/**
 * The [RoomDatabase] for this app
 */
@Database(
    entities = [User::class],
    version = 4,
    exportSchema = false
)
abstract class MobileComputingDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}