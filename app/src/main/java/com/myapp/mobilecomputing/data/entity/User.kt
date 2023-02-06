package com.myapp.mobilecomputing.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index("username", unique = true)
    ]
)
data class User(
    @PrimaryKey @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String
)
