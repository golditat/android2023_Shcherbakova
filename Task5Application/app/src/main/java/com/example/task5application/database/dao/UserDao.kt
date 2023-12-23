package com.example.task5application.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.Update
import com.example.task5application.database.entity.UserEntity
import com.example.task5application.model.User


@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun getUserByEmail(email: String):UserEntity?

    @Query("SELECT * FROM user WHERE phone LIKE :phone")
    fun getUserByPhone(phone: String):UserEntity?

    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun getUserByID(id: Int):UserEntity?

    @Insert(onConflict = 1)
    fun insertNewUser(user: UserEntity)

    @Query("DELETE FROM user WHERE id == :id")
    fun deleteUser(id:Int)

    @Update(entity = UserEntity::class)
    fun updateUserInfo(user: UserEntity)
}