package com.example.task5application.model

import androidx.room.ColumnInfo

class Film(
    val id:Int,
    val imagesrc:String,
    val name:String,
    val year:Int,
    val country:String,
    val grade:Double,
    val isLike: Like)