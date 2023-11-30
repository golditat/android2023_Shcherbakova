package com.example.task4application.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task4application.R
import com.example.task4application.databinding.FragmentThirdBinding
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class ThirdFragment:Fragment(R.layout.fragment_third) {

    val binding:FragmentThirdBinding by viewBinding(FragmentThirdBinding::bind)
    var coroutines: ArrayList<Deferred<Unit>> = ArrayList()
    var stoponbeckground:Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            btnExecuteCoroutines.setOnClickListener {
                coroutines.clear()
                var countCoroutines = seekBarCoroutines.progress
                var async = checkBoxAsync.isChecked
                stoponbeckground = checkBoxStopOnBackground.isChecked
                lifecycleScope.launch {
                    var count = 0
                    if (async) {
                        repeat(countCoroutines) {
                            val asyncCor = async(Dispatchers.IO, start = CoroutineStart.DEFAULT) {
                                getData(number = ++count)
                            }
                            coroutines.add(asyncCor)
                        }
                        coroutines.awaitAll()
                    } else {
                        repeat(countCoroutines) {
                            val asyncCor = async(Dispatchers.IO, start = CoroutineStart.LAZY) {
                                getData(number = ++count)
                            }
                            coroutines.add(asyncCor)
                        }
                        coroutines.forEach { it.await() }
                    }
                    showNotification()
                }
            }
        }
    }

    private fun showNotification() {
        val notificationManager = ContextCompat.getSystemService(
            requireContext(),
            NotificationManager::class.java
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel Description"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(requireContext(), "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My Job is Done")
            .setContentText("All coroutines have completed their work.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onPause(){
        super.onPause()
        if(stoponbeckground) {
            for (i in 0..coroutines.size - 1) {
                coroutines.get(i).cancel()

            }
            println("TEST_TAG - stop coroutines ${coroutines.size}")
            coroutines.clear()
        }
    }

    private suspend fun getData(number: Int){
        delay(timeMillis = getRandom() + 500)
        println("TEST_TAG - My job here is done ${number}")
    }

    private fun getRandom(): Long {
        val rand = Random.nextInt(1, 5)
        return rand * 1000L
    }
}