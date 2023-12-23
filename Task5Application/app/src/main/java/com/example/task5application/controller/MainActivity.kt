package com.example.task5application.controller

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.databinding.ActivityMainBinding
import com.example.task5application.fragments.AutorisationFragment
import com.example.task5application.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false)
        if (isUserLoggedIn) {
            var fragment:MainFragment = MainFragment()
            var bundle:Bundle = Bundle()
            bundle.putInt("USERID", sharedPreferences.getInt("userId", 0))
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, AutorisationFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}