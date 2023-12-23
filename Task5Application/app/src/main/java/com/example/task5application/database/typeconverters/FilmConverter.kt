package com.example.task5application.database.typeconverters

import androidx.room.TypeConverter
import com.example.task5application.model.Film
import com.example.task5application.model.User
import com.google.gson.Gson

class FilmConverter {

    @TypeConverter
    fun serialize(film: Film):String{
        val gson = Gson()
        return try {
            gson.toJson(film)
        } catch (ex: Exception) {
            ""
        }
    }

    @TypeConverter
    fun deserialize(filmEntity: String): Film?{
        return try {
            val gson = Gson()
            gson.fromJson(filmEntity, Film::class.java)
            null
        } catch (ex: Exception) {
            null
        }
    }
}