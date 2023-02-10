package com.myapp.mobilecomputing


import android.content.Context
import androidx.room.DatabaseConfiguration
import androidx.room.Room
import com.myapp.mobilecomputing.data.repository.ReminderRepository
import com.myapp.mobilecomputing.data.repository.UserRepository
import com.myapp.mobilecomputing.data.room.MobileComputingDatabase

/**
 * A simple singleton dependency graph
 *
 * For a real app, please use something like Koin/Dagger/Hilt instead
 */
object Graph {
    lateinit var database: MobileComputingDatabase

    lateinit var appContext: Context

    val userRepository by lazy {

            UserRepository(
                userDao = database.userDao()
            )

    }

    val reminderRepository by lazy {

        ReminderRepository(
            reminderDao = database.reminderDao()
        )

    }


    fun provide(context: Context) {
        this.appContext = context
        this.database = Room.databaseBuilder(context, MobileComputingDatabase::class.java, "mcData.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}