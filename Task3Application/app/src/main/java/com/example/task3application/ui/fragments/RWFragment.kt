package com.example.task3application.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task3application.R
import com.example.task3application.databinding.FragmentRwBinding
import com.example.task3application.ui.adapter.RWAdapter

class RWFragment:Fragment(R.layout.fragment_rw) {
    val binding:FragmentRwBinding by viewBinding(FragmentRwBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var vpAdapter:RWAdapter
        var dataUpdate:Int
        var du:Boolean
        if(!(arguments?.getString("DATAUPDATE").equals("")) && arguments?.getString("DATAUPDATE")!= null ){
            dataUpdate = arguments?.getString("DATAUPDATE")?.toInt()!!
            du = false
        }else{
            dataUpdate = 0
            du = true
            if (arguments?.getString("DATA")?.toInt()!! <= 12) {
                binding.galereyRv.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            } else {
                binding.galereyRv.layoutManager = GridLayoutManager(this.context, 2)
            }
        }
        Log.e("dataupdate", dataUpdate.toString())
        if(du) {
            val itemCount: Int = arguments?.getString("DATA")?.toInt() ?: 0
            if(itemCount == 0){
                binding.noelementsTv.visibility = View.VISIBLE
            }
            vpAdapter = arguments?.getSerializable("ADAPTER") as RWAdapter
        }else{
            binding.noelementsTv.visibility = View.INVISIBLE
            vpAdapter = arguments?.getSerializable("ADAPTER") as RWAdapter
            vpAdapter.items.addNewItems(dataUpdate)
        }
        binding?.run{
            galereyRv.adapter = vpAdapter
            var bundle:Bundle = Bundle()
            bundle.putSerializable("ADAPTER", vpAdapter)
            var fragment:Fragment = BottomShitFragment()
            fragment.arguments = bundle
            addBtn.setOnClickListener{
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
        }
    }
}