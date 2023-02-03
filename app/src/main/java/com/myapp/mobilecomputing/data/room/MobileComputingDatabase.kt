package com.codemave.mobilecomputing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Payment
import com.codemave.mobilecomputing.data.entity.User

/**
 * The [RoomDatabase] for this app
 */
@Database(
    entities = [User::class],
    version = 2,
    exportSchema = false
)
abstract class MobileComputingDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}