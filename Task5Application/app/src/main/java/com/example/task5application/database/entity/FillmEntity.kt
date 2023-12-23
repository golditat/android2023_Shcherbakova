package com.example.task5application.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "film",
    indices = [Index("grade"), Index("id")]
)
data class FillmEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name="imagesrc") val imagesrc:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "year") val year:Int,
    @ColumnInfo(name="country") val country:String,
    @ColumnInfo(name = "grade") val grade:Double
)