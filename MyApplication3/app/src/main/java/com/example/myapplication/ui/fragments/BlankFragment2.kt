package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBlank2Binding


class BlankFragment2 : Fragment(R.layout.fragment_blank2) {
    var binding:FragmentBlank2Binding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlank2Binding.bind(view)
        binding?.run{
            val userString:String? = getArguments()?.getString("USERSTRING")
            if(!(userString.equals(""))){
                tw.text = userString
            }
            val bundle:Bundle = Bundle()
            bundle.putString("USERSTRING", userString)
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("USERSTRING", binding?.tw?.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val userString = savedInstanceState?.getString("USERSTRING")
        if (!userString.isNullOrEmpty()) {
            binding?.tw?.text = userString
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}