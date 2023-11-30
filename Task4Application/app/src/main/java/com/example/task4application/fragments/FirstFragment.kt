package com.example.task4application.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task4application.Notificate
import com.example.task4application.NotificationHandler
import com.example.task4application.R
import com.example.task4application.databinding.FragmentFirstBinding

class FirstFragment:Fragment(R.layout.fragment_first) {
    val  notificationsHendler:NotificationHandler = NotificationHandler()
    val binding:FragmentFirstBinding by viewBinding(FragmentFirstBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run{
            btnShowNotification.setOnClickListener {
                var importance = Notificate.getimportance()
                var privacy = Notificate.getprivacy()
                var detailed = Notificate.getdetailed()
                var buttons = Notificate.getbuttons()
                var title:String = editTextTitle.text.toString()
                var message:String= editTextMessage.text.toString()
                var detailedtext:String = editTextMessageDetail.text.toString()
                notificationsHendler.createNotification(ctx=requireContext(), 122, chanelpriority = importance, title = title, message,detailedtext, privacy, detailed, buttons)
            }
        }

    }
}