package com.example.task5application.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.task5application.database.entity.FillmEntity
import com.example.task5application.model.Film
import com.example.task5application.model.User

@Dao
interface FilmDao {

    @Query("SELECT * FROM film WHERE id == :id")
    fun getFilmByID(id: Int): FillmEntity?

    @Insert
    fun insertNewFilm(film: FillmEntity)

    @Query("DELETE FROM film WHERE id == :id")
    fun deleteFilm(id:Int)

    @Update
    fun updateFilmInfo(film: FillmEntity)

    @Query("SELECT * FROM film")
    fun getAllFilms():List<FillmEntity>?

    @Query("SELECT film.* FROM film JOIN likes ON film.id = likes.filmid WHERE likes.userid = :id")
    fun getLikedFilms(id:Int):List<FillmEntity>
}