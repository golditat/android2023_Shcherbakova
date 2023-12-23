package com.example.task5application.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.database.entity.UserEntity
import com.example.task5application.databinding.FragmentAutorisationBinding
import com.example.task5application.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AutorisationFragment:Fragment(R.layout.fragment_autorisation) {
    val binding:FragmentAutorisationBinding by viewBinding(FragmentAutorisationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            logInBtn.setOnClickListener {
                var email = editTextTextEmailAddress.text.toString()
                var password = editTextTextPassword.text.toString()
                var flag = false
                var userid:Int = 0
                lifecycleScope.launch {
                    val user: UserEntity? = async(Dispatchers.IO) {
                        Synchronizer.getDbInstance().userDao.getUserByEmail(email =email)
                    }.await()
                    if(user != null && user.password.equals(password)){
                        flag = true
                        userid = user.id
                    }
                }
                if(flag) {
                    Log.e("userid", userid.toString())
                    val sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isUserLoggedIn", true)
                    editor.putInt("userId", userid)
                    editor.apply()
                    var fragment:MainFragment = MainFragment()
                    var bundle = Bundle()
                    bundle.putInt("USERID", userid)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }else{
                    textView.text = "No user"
                }
            }
            signUpBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RegistrationFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}