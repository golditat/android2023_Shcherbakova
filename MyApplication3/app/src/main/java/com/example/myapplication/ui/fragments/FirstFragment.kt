package com.example.myapplication.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.utils.UsersString


class FirstFragment : Fragment(R.layout.fragment_first) {
    var binding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        if (savedInstanceState != null) {
            val userInput = savedInstanceState.getString("USER_INPUT", "")
            binding?.it1?.setText(userInput)
        }
        binding?.run{
            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                button1.isEnabled = true
                button1.visibility =View.VISIBLE
                button1.setOnClickListener{
                    if(it1.text != null) {
                        val fragment3 =
                            requireActivity().supportFragmentManager
                                .findFragmentById(R.id.fragment_container_3) as BlankFragment3
                        fragment3.updateData(it1.text.toString())
                        it1.setText("")
                    }
                }
            }
            button.setOnClickListener{
                    val bundle: Bundle = Bundle()
                    if (!(it1.text.toString().equals("enter text"))) {
                        bundle.putString("USERSTRING", it1.text.toString())
                    }
                    val fragment: BlankFragment = BlankFragment()
                    fragment.arguments = bundle
                    val fragment2: BlankFragment2 = BlankFragment2()
                    fragment2.arguments = bundle
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_1, fragment)
                        .addToBackStack(null)
                        .commit()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_1, fragment2)
                        .addToBackStack(null)
                        .commit()

            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("USERSTRING", binding?.tw?.text.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}