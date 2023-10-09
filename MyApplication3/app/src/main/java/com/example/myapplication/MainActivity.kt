package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.fragments.BlankFragment3
import com.example.myapplication.ui.fragments.FirstFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate( savedInstanceState)
        setContentView( R.layout.activity_main)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_2, FirstFragment())
                .addToBackStack(null)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_3, BlankFragment3())
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_1, FirstFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}