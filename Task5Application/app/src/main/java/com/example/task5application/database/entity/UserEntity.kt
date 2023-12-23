package com.example.task5application.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.IMultiInstanceInvalidationCallback.Default
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["email"],
        unique = true), Index(value = ["phone"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name="phone") var phone:String,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name="password") var password:String
)
