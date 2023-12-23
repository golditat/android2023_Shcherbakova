package com.example.task5application.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "likes",
    indices = [Index("userid")],
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userid"], onDelete = ForeignKey.NO_ACTION),
    ForeignKey(entity = FillmEntity::class, parentColumns = ["id"], childColumns = ["filmid"], onDelete = ForeignKey.NO_ACTION)],
)
data class LikeEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "userid") val userid:Int,
    @ColumnInfo(name="filmid") val filmid:Int
)