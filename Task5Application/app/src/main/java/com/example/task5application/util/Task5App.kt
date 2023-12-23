package com.example.task5application.util

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.fragments.MainFragment

class Task5App:Application() {
    override fun onCreate() {
        super.onCreate()
        Synchronizer.initData(this)
    }
}