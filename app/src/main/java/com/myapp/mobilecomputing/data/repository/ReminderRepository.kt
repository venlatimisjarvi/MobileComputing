package com.myapp.mobilecomputing.data.repository

import com.myapp.mobilecomputing.data.entity.Reminder
import com.myapp.mobilecomputing.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

/**
 * A data repository for [Reminder] instances
 */
class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    /**
     * Returns a flow containing the list of payments associated with the creator with the
     * given [creatorId]
     */
    fun remindersForCreator(creatorId: Long) : Flow<List<Reminder>> {
        return reminderDao.remindersForCreator(creatorId)
    }
    suspend fun reminder(reminderId : Long) : Reminder?{
        return reminderDao.reminder(reminderId)
    }

    /**
     * Add a new [Reminder] to the payment store
     */
    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
    suspend fun deleteReminder(reminder: Reminder) = reminderDao.delete(reminder)
    suspend fun updateReminder(reminder: Reminder) = reminderDao.update(reminder)
}