package com.example.task5application.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.task5application.database.entity.LikeEntity
import com.example.task5application.model.Like


@Dao
interface LikeDao {

    @Query("SELECT * FROM likes WHERE id LIKE :id")
    fun getLike(id:Int):LikeEntity?

    @Query("SELECT * FROM likes WHERE userid == :userid AND filmid == :filmid")
    fun getLikeFILMANDUSER(userid:Int, filmid:Int):LikeEntity?

    @Query("SELECT * FROM likes WHERE userid LIKE :userid")
    fun getUserLikes(userid:Int):List<LikeEntity>?

    @Query("DELETE FROM likes WHERE id == :id")
    fun deleteLike(id:Int)

    @Insert
    fun createLike(like: LikeEntity)

    @Query("DELETE FROM likes WHERE filmid == :id")
    fun deleteallofFilm(id:Int)

}