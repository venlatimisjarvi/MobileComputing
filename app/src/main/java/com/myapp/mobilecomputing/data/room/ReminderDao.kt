package com.myapp.mobilecomputing.data.room



import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.myapp.mobilecomputing.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao {
    @Query("""
        SELECT * FROM reminders
        WHERE creator_id = :creatorId
    """)
    abstract fun remindersForCreator(creatorId: Long): Flow<List<Reminder>>

    @Query("""SELECT * FROM reminders WHERE id = :reminderId""")
    abstract suspend fun reminder(reminderId: Long): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int
}