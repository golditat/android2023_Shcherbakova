package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment : Fragment(R.layout.fragment_blank) {

    var binding: FragmentBlankBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlankBinding.bind(view)
        binding?.run{
            val userString:String? = getArguments()?.getString("USERSTRING")
            if(!(userString.equals(""))){
                tw.text = userString
            }
            val bundle: Bundle = Bundle()
            bundle.putString("USERSTRING", userString)
            button2.setOnClickListener{
               val fragment = FirstFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_1, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            button3.setOnClickListener{
                val fragment = BlankFragment2()
                fragment.arguments = bundle
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container_1, fragment)
                transaction.commit()
            }
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