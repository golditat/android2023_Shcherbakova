package com.example.task5application.database.typeconverters

import androidx.room.TypeConverter
import com.example.task5application.model.Like
import com.google.gson.Gson

class LikeConverter {
    @TypeConverter
    fun serialize(like: Like):String{
        val gson = Gson()
        return try {
            gson.toJson(like)
        } catch (ex: Exception) {
            ""
        }
    }

    @TypeConverter
    fun deserialize(likeEntity: String): Like?{
        return try {
            val gson = Gson()
            gson.fromJson(likeEntity, Like::class.java)
            null
        } catch (ex: Exception) {
            null
        }
    }
}