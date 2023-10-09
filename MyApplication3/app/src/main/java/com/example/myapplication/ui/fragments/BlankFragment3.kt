package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBlank3Binding
import com.example.myapplication.utils.UsersString

class BlankFragment3 : Fragment(R.layout.fragment_blank3) {

    var binding: FragmentBlank3Binding? = null
    var firstString:String?=null
    var secondString:String?=null
    var thirdString:String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlank3Binding.bind(view)
        binding?.run{
            textView4.setText(firstString)
            textView2.setText(secondString)
            textView3.setText(thirdString)
        }

    }
    fun updateData(newData:String) {
        if(firstString!= null){
            if(secondString!= null){
                if(thirdString!= null){
                    thirdString = secondString
                    secondString = firstString
                    firstString = newData
                }else{
                    thirdString = newData
                }
            }else{
                secondString = newData
            }
        }else{
            firstString = newData
        }
        binding?.run {
            textView4.setText(firstString)
            textView2.setText(secondString)
            textView3.setText(thirdString)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("FIRST_STRING", firstString)
        outState.putString("SECOND_STRING", secondString)
        outState.putString("THIRD_STRING", thirdString)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        firstString = savedInstanceState?.getString("FIRST_STRING", "") ?: ""
        secondString = savedInstanceState?.getString("SECOND_STRING", "") ?: ""
        thirdString = savedInstanceState?.getString("THIRD_STRING", "") ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}