package com.example.task2application.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task2application.R
import com.example.task2application.databinding.FragmentStartBinding
import com.example.task2application.ui.data.QuestionsData

class StartFragment:Fragment(R.layout.fragment_start) {
    val binding:FragmentStartBinding by viewBinding(FragmentStartBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var flag = false
        binding?.run{
            phoneTiet.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val phoneNumber = phoneTiet.text.toString()
                    Log.e("Phone", phoneNumber)
                    if (isValidPhoneNumber(phoneNumber)) {
                        flag = true
                    } else {
                        Toast.makeText(activity, "Некорректный номер телефона", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            var flag1:Boolean = false
            phoneTiet.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(!flag1) {
                        val phone = s.toString()
                        flag1 = true
                        if (phone.equals("+7") || phone.equals("8")) {
                            phoneTiet.setText(phone + "(")
                        } else if (((phone.length == 6) || (phone.length == 5)) && isValidPhoneNumber(phone)){
                            phoneTiet.setText(phone + ")")
                        } else if (((phone.length == 10 ) || (phone.length == 9)) && isValidPhoneNumber(phone)) {
                            phoneTiet.setText(phone + "-")
                        } else if (((phone.length == 13 ) || (phone.length == 12)) && isValidPhoneNumber(phone)) {
                            phoneTiet.setText(phone + "-")
                        }
                        flag1 = false
                        phoneTiet.setSelection(phoneTiet.text.toString().length)
                    }

                }

            })
            var flag2 = false
            questionsTiet.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val questionNumber = s.toString()
                    Log.e("questions", questionNumber)
                    if (!(questionNumber.isEmpty()) && Integer.parseInt(questionNumber) >= 0 && flag) {
                        flag2 = true
                        startTestBtn.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(activity, "Введите количество вопросов", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
            startTestBtn.setOnClickListener{
                val bundle: Bundle = Bundle()
                var data:QuestionsData = QuestionsData(questionsTiet.text.toString().toInt())
                bundle.putSerializable("DATA", data)
                bundle.putString("NUMBER", questionsTiet.text.toString())
                val fragment = ViewPagerFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches("(\\+7|8)\\(9\\d{2}(\\)\\d{3}(-\\d{2}(-\\d{2})?)?)?".toRegex())
    }

}