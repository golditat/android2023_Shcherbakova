package com.example.task4application.fragments

import android.os.Bundle
import android.service.autofill.Dataset
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task4application.R
import com.example.task4application.Notificate
import com.example.task4application.databinding.FragmentSecondBinding

class SecondFragment:Fragment(R.layout.fragment_second) {
    val binding:FragmentSecondBinding by viewBinding (FragmentSecondBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var importance:Int
        var privacy:Boolean
        var buttons:Boolean
        var detailedText:Boolean
        binding.run {
            if (savedInstanceState != null) {
                importance = Notificate.getimportance()
                privacy = Notificate.getprivacy()
                buttons = Notificate.getbuttons()
                detailedText = Notificate.getdetailed()
                    when (importance) {
                        0 -> {
                            radioButtonMedium.isChecked = true
                        }

                        1 -> {
                            radioButtonHigh.isChecked = true
                        }

                        2 -> {
                            radioButtonUrgent.isChecked = true
                        }
                    }
                    if (privacy) {
                        checkBoxPrivate.isChecked = true
                    }
                    if (detailedText ) {
                        checkBoxDetailedText.isChecked = true
                    }
                    if (buttons) {
                        checkBoxShowButtons.isChecked = true
                    }
            }
            radioGroupPriority.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioButtonMedium -> {
                        Notificate.setimportance(0)
                    }
                    R.id.radioButtonHigh -> {
                        Notificate.setimportance(1)
                    }
                    R.id.radioButtonUrgent -> {
                        Notificate.setimportance(2)
                    }
                }
            }
            checkBoxPrivate.setOnClickListener{
                Notificate.setprivacy(checkBoxPrivate.isChecked)
            }
            checkBoxDetailedText.setOnClickListener {
                Notificate.setdetailed(checkBoxDetailedText.isChecked)
            }
            checkBoxShowButtons.setOnClickListener {
                Notificate.setbuttons(checkBoxShowButtons.isChecked)
            }

        }
    }
}
