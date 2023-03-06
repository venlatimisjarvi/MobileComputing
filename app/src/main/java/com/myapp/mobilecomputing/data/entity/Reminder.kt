package com.myapp.mobilecomputing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "reminders",
    indices = [
        Index("id", unique = true)
    ]
)

data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val reminderId: Long = 0,
    @ColumnInfo(name = "message") val message : String,
    @ColumnInfo(name = "location_x") val locationX : Double?,
    @ColumnInfo(name = "location_y") val locationY : Double?,
    @ColumnInfo(name = "reminder_time") val reminderTime : Long?,
    @ColumnInfo(name = "creation_time") val creationTime: Long,
    @ColumnInfo(name = "creator_id") val creatorId : Long,
    @ColumnInfo(name = "reminder_seen") val reminderSeen : Boolean
)
