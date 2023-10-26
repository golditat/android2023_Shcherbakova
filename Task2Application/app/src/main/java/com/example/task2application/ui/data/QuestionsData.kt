package com.example.task2application.ui.data

import android.util.Log
import java.io.Serializable
import kotlin.random.Random

data class QuestionsData( val questionsCount:Int) : Serializable{
    val repo:CatalogRepository = CatalogRepository(questionsCount)
    var selectedAnswers:Int = 0
    var ansvers:ArrayList<String> = ArrayList()
}
class CatalogRepository : Serializable {
    var itemList: ArrayList<Question> = ArrayList()
    constructor(questionsNumber:Int){
        for(i in 1 .. questionsNumber){
            val thisQuestion:Question = Question(i)
            itemList.add(thisQuestion)
        }
    }
}
class Question() : Serializable {
    var id :String = ""
    var task:String = ""
    var variants: ArrayList<String> = ArrayList()
    var qId:Int = 0

    constructor(questionID: Int) : this() {
        id = "question " + questionID
        task = "task " + questionID
        qId = questionID - 1
        for(i in 1 .. Random.nextInt(5, 11)){
            variants.add("ansver " + i)
            Log.e("variant",i.toString())
        }
    }
}