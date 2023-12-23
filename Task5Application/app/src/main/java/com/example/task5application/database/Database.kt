package com.example.task5application.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.task5application.database.dao.FilmDao
import com.example.task5application.database.dao.LikeDao
import com.example.task5application.database.dao.UserDao
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.database.entity.LikeEntity
import com.example.task5application.database.entity.UserEntity
import com.example.task5application.database.typeconverters.FilmConverter
import com.example.task5application.database.typeconverters.UserConverter

@Database(
    entities = [UserEntity::class, FillmEntity::class, LikeEntity::class],
    version = 1
)
@TypeConverters(UserConverter::class, FilmConverter::class)
abstract class Database :RoomDatabase(){

    abstract val userDao:UserDao

    abstract val filmDao:FilmDao

    abstract val likeDao:LikeDao

}