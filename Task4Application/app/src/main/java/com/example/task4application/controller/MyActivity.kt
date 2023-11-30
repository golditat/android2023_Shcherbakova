package com.example.task4application.controller

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.task4application.PermissionRequestHandler
import com.example.task4application.R
import com.example.task4application.fragments.FirstFragment
import com.example.task4application.fragments.SecondFragment
import com.example.task4application.fragments.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyActivity:AppCompatActivity() {
    lateinit var permissionRequestHandler:PermissionRequestHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.action_main -> selectedFragment = FirstFragment()
                R.id.action_notifications -> selectedFragment = SecondFragment()
                R.id.action_coroutines -> selectedFragment = ThirdFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }
        if(savedInstanceState == null){
            permissionRequestHandler = PermissionRequestHandler(
                activity = this,
                callback = {
                    Toast.makeText(this, "Granted", Toast.LENGTH_LONG).show()
                }
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (getIntent().getBooleanExtra("open_fragment", true)) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SecondFragment())
                .addToBackStack(null)
                .commit();
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FirstFragment())
            .commit()
    }
    fun requestPermission(permission: String) {
        // ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        permissionRequestHandler?.requestPermission(permission)
    }
}