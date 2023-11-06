package com.example.task3application.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task3application.R
import com.example.task3application.databinding.FragmentBottomShitBinding
import com.example.task3application.ui.adapter.RWAdapter

class BottomShitFragment: Fragment(R.layout.fragment_bottom_shit) {
    val binding:FragmentBottomShitBinding by viewBinding(FragmentBottomShitBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle:Bundle = Bundle()
        var adapter:RWAdapter = arguments?.getSerializable("ADAPTER") as RWAdapter
        binding.run {
            var text:String = ""
            addTiet.addTextChangedListener(object : TextWatcher {
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
                    text = addTiet.text.toString()
                    if(text.equals("") || text.toInt() <= 5){
                        addTil.error = ""
                        saveBtn.isClickable = true
                    }else if(text.toInt() > 5){
                        addTil.error = ">5"
                        saveBtn.isClickable = false
                    }
                    Log.e("text in update",text)
                    bundle.putSerializable("ADAPTER", adapter)
                    var update = addTiet.text.toString()
                    if((update != null) && !(update.equals(""))) {
                        bundle.putString("DATAUPDATE", update)
                        Log.e("count in bs", update)
                    } else {
                        bundle.putString("DATAUPDATE", "0")
                    }
                }
            })
            saveBtn.setOnClickListener {
                var fragment: Fragment = RWFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
        }
    }
}