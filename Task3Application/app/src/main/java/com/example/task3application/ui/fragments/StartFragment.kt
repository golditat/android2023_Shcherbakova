package com.example.task3application.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task3application.R
import com.example.task3application.databinding.FragmentStartBinding
import com.example.task3application.ui.adapter.RWAdapter

class StartFragment:Fragment(R.layout.fragment_start) {
    val binding:FragmentStartBinding by viewBinding(FragmentStartBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            startTiet.addTextChangedListener(object : TextWatcher{
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
                    if(startTiet.text.toString().equals("") || startTiet.text.toString().toInt() <= 45){
                        startTil.error = ""
                        button.isClickable = true
                    }else if(startTiet.text.toString().toInt() > 45){
                        startTil.error = ">45"
                        button.isClickable = false
                    }
                }
            })
            button.setOnClickListener{
                var  vpAdapter:RWAdapter
                var bundle:Bundle = Bundle()
                var fragment:Fragment = RWFragment()
                if((startTiet.text!= null) && !(startTiet.text.toString().equals(""))) {
                    bundle.putString("DATA", startTiet.text.toString())
                    vpAdapter= RWAdapter( startTiet.text.toString().toInt(), requireContext(), requireActivity().supportFragmentManager)
                }else{
                    bundle.putString("DATA", "0")
                    vpAdapter= RWAdapter( 0, requireContext(), requireActivity().supportFragmentManager)
                }
                bundle.putSerializable("ADAPTER",vpAdapter)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}