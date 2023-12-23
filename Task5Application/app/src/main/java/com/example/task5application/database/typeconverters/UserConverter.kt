package com.example.task5application.database.typeconverters

import androidx.room.TypeConverter
import com.example.task5application.model.User
import com.google.gson.Gson

class UserConverter {

    @TypeConverter
    fun serialize(user: User):String{
        val gson = Gson()
        return try {
            gson.toJson(user)
        } catch (ex: Exception) {
            ""
        }
    }

    @TypeConverter
    fun deserialize(userEntity: String):User?{
        return try {
            val gson = Gson()
            gson.fromJson(userEntity, User::class.java)
            null
        } catch (ex: Exception) {
            null
        }
    }
}