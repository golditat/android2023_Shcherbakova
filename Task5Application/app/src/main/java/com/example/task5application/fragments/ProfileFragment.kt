package com.example.task5application.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task5application.R
import com.example.task5application.database.connectors.Synchronizer
import com.example.task5application.database.entity.UserEntity
import com.example.task5application.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class ProfileFragment:Fragment(R.layout.fragment_profile) {
    val binding:FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)
    lateinit var user:UserEntity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            var userid = arguments?.getInt("USERID")?:0
            Log.e("userid", userid.toString())
            lifecycleScope.launch {
                async {
                    getUser(userid)
                }.await()
                editTextText6.setText(user.name)
                editTextPhone2.setText(user.phone)
                editTextTextEmailAddress3.setText(user.email)
                editTextTextPassword.setText(user.password)
                deleteProfileBtn.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        Synchronizer.getDbInstance().userDao.deleteUser(userid)
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AutorisationFragment())
                        .addToBackStack(null)
                        .commit()
                }
                logOutBtn.setOnClickListener {
                    //выход из профиля
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AutorisationFragment())
                        .addToBackStack(null)
                        .commit()
                }
                imageButton2.setOnClickListener {
                    var fragment = MainFragment()
                    var bundle = Bundle()
                    bundle.putInt("USERID", userid)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                editTextPhone2.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        button.visibility = View.VISIBLE
                    }
                })
                editTextTextPassword.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        button.visibility = View.VISIBLE
                    }
                })
                editTextTextEmailAddress3.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        button.visibility = View.VISIBLE
                    }
                })
                editTextText6.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        button.visibility = View.VISIBLE
                    }
                })
                button.setOnClickListener {
                    var flag:Boolean = true
                    var newphone = editTextPhone2.text.toString()
                    var newemail = editTextTextEmailAddress3.text.toString()
                    var newname = editTextText6.text.toString()
                    var newPass = editTextTextPassword.text.toString()
                    lifecycleScope.launch(Dispatchers.IO) {
                            val userOnPhone = Synchronizer.getDbInstance().userDao.getUserByPhone(newphone)
                            val userOnEmail = Synchronizer.getDbInstance().userDao.getUserByEmail(newemail)

                            if ((userOnPhone != null && userOnPhone.id != user.id) || (userOnEmail != null && userOnEmail.id != user.id)) {
                                flag = false
                            } else {
                                user.email = newemail
                                user.phone = newphone
                                user.name = newname
                                user.password = newPass
                                Synchronizer.getDbInstance().userDao.updateUserInfo(user)
                            }
                    }
                    if(!flag){
                        textView4.setText("Exeption")
                    }else{
                        textView4.setText("")
                    }
                }
            }
        }
    }

    private suspend fun getUser(id: Int) {
        withContext(Dispatchers.IO) {
            user = Synchronizer.getDbInstance().userDao.getUserByID(id) ?: UserEntity(0, "unknown", "00000", "unknown", "00000")
        }
    }
}