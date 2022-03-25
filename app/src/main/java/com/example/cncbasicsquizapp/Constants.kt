package com.example.cncbasicsquizapp

import java.util.*
import kotlin.collections.ArrayList

object Constants {

    fun qetQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        val question1 = Question(
            id = 1, "Description of this code is ...", R.drawable.g00,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question2 = Question(
            id = 2, "Description of this code is ...", R.drawable.g01,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question3 = Question(
            id = 3, "Description of this code is ...", R.drawable.g02,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question4 = Question(
            id = 3, "Description of this code is ...", R.drawable.g03,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question5 = Question(
            id = 3, "Description of this code is ...", R.drawable.g04,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question6 = Question(
            id = 3, "Description of this code is ...", R.drawable.g20,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        val question7 = Question(
            id = 3, "Description of this code is ...", R.drawable.g21,
            "answer1", "answer2", "answer3", "answer4", 1
        )
        Collections.addAll(
            questionsList, question1, question2, question3, question4, question5,
            question6, question7
        )

        return questionsList
    }

}