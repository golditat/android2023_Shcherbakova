package com.example.task2application.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task2application.R
import com.example.task2application.databinding.FragmentViewPagerBinding
import com.example.task2application.ui.adapters.ViewPagerAdapter
import com.example.task2application.ui.data.QuestionsData

class ViewPagerFragment:Fragment(R.layout.fragment_view_pager) {
    val binding:FragmentViewPagerBinding by viewBinding(FragmentViewPagerBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data:QuestionsData = arguments?.getSerializable("DATA") as QuestionsData
        var vpAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle, data)
        binding?.run{
            questionsVp.adapter = vpAdapter
        }

    }
}