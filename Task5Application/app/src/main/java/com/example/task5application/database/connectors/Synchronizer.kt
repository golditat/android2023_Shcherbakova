package com.example.task5application.database.connectors

import android.content.Context
import androidx.room.Room
import com.example.task5application.database.Database

object Synchronizer {
    private var dbInstance: Database? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, Database::class.java, "task5app.db")
            .build()

    }

    fun getDbInstance(): Database {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }

}