package com.example.task5application.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.database.entity.UserEntity
import com.example.task5application.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationFragment:Fragment(R.layout.fragment_registration) {
    val binding:FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            signInBtn.setOnClickListener {
                var flag = false
                //валидация полей
                var name = nameeditTextText.text.toString()
                var email = editTextTextEmailAddress2.text.toString()
                var phone = editTextPhone.text.toString()
                var password = editTextTextPassword2.text.toString()
                if(name != null && password!= null && validPhone(phone) && validemail(email) ){
                    flag = true
                }
                if(flag) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        Synchronizer.getDbInstance().userDao.insertNewUser(
                            UserEntity(
                                0,
                                name,
                                phone,
                                email,
                                password
                            )
                        )
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AutorisationFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    fun validPhone(phone:String):Boolean{
        val russianPhoneRegex = Regex("^((\\+7)|8)\\d{10}\$")

        return russianPhoneRegex.matches(phone)
    }

    fun validemail(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}