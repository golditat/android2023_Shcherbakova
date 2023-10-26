package com.example.task2application.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task2application.ui.data.Question
import com.example.task2application.ui.data.QuestionsData
import com.example.task2application.ui.fragments.QuestionFragment

class ViewPagerAdapter(manager:FragmentManager, lifecycle: Lifecycle, repo:QuestionsData):FragmentStateAdapter(manager, lifecycle) {
    val data:QuestionsData= repo
    override fun getItemCount(): Int {
        return data.repo.itemList.size * INFINITE_LOOP_FACTOR
    }

    override fun createFragment(position: Int): Fragment {
        val adjustedPosition = position % data.repo.itemList.size
        return QuestionFragment.newInstance(data.repo.itemList[adjustedPosition], adjustedPosition, data)
    }
    companion object {
        private const val INFINITE_LOOP_FACTOR = 1000
    }
}