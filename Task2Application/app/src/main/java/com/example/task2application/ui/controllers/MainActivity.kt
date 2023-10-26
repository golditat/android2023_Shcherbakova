package com.example.task2application.ui.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2application.R
import com.example.task2application.ui.fragments.StartFragment

class MainActivity:AppCompatActivity() {
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate( savedInstanceState)
        setContentView( R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, StartFragment())
            .addToBackStack(null)
            .commit()

    }
}