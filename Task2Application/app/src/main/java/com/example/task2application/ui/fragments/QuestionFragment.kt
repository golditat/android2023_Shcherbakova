package com.example.task2application.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task2application.R
import com.example.task2application.databinding.FragmentQuestionBinding
import com.example.task2application.ui.data.Question
import com.example.task2application.ui.data.QuestionsData

@Suppress("DEPRECATION")
class QuestionFragment:Fragment(R.layout.fragment_question) {
    val binding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)
    lateinit var data: QuestionsData
    companion object {
        private const val ARG_QUESTION = "question"
        private const val DATA = "data"

        fun newInstance(question: Question, questionID:Int, data:QuestionsData): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putSerializable(ARG_QUESTION, question)
            args.putSerializable(DATA, data)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            data = it.getSerializable("data")!! as QuestionsData
        }
        val question = arguments?.getSerializable(ARG_QUESTION) as? Question
        var questionID: Int = question?.qId ?: 0
        if (question != null) {
            binding?.run {
                numberQuestionTv.text = question.id
                questionTv.text = question.task
                if(data.ansvers.size == questionID){
                    data.ansvers.add("")
                }
                for (i in 1..question.variants.size) {
                    var radio = RadioButton(requireContext())
                    radio.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    radio.buttonTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))
                    radio.textSize = 20f
                    radio.text = question.variants.get(i - 1)
                    if(data.ansvers.get(questionID).equals(radio.text)){
                        radio.isChecked = true
                    }
                    radio.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            data.selectedAnswers++
                            data.ansvers.set(questionID, radio.text.toString())
                        } else {
                            data.selectedAnswers--
                        }
                        if (data.selectedAnswers == data.repo.itemList.size) {
                            button4.visibility = View.VISIBLE
                        }
                    }
                    ansversRg.addView(radio)
                }
                if (data.selectedAnswers == data.repo.itemList.size) {
                    button4.visibility = View.VISIBLE
                }
                button4.setOnClickListener {
                    Toast.makeText(requireContext(), "Тест пройден", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}