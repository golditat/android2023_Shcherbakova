package com.example.task3application.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task3application.R
import com.example.task3application.databinding.FragmentDetailInfoBinding
import com.example.task3application.ui.data.Item

class DetailInfoFragment : Fragment(R.layout.fragment_detail_info) {
    val binding:FragmentDetailInfoBinding by viewBinding(FragmentDetailInfoBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item: Item = arguments?.getSerializable("ITEM") as Item
        var transitionName:String = arguments?.getString("TRANSITIONNAME") as String
        binding.run {
            photoIv.transitionName = transitionName
            photoIv.setImageDrawable(item.image)
        }
    }
}