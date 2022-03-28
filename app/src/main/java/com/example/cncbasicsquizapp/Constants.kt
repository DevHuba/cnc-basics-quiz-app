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
            "answer1-2", "answer2-2", "answer3-2", "answer4-2", 1
        )
        val question3 = Question(
            id = 3, "Description of this code is ...", R.drawable.g02,
            "answer1-3", "answer2-3", "answer3-3", "answer4-3", 1
        )
        val question4 = Question(
            id = 3, "Description of this code is ...", R.drawable.g03,
            "answer1-4", "answer2-4", "answer3-4", "answer4-4", 1
        )
        val question5 = Question(
            id = 3, "Description of this code is ...", R.drawable.g04,
            "answer1-5", "answer2-5", "answer3-5", "answer4-5", 1
        )
        val question6 = Question(
            id = 3, "Description of this code is ...", R.drawable.g20,
            "answer1-6", "answer2-6", "answer3-6", "answer4-6", 1
        )
        val question7 = Question(
            id = 3, "Description of this code is ...", R.drawable.g21,
            "answer1-7", "answer2-7", "answer3-7", "answer4-7", 1
        )
        Collections.addAll(
            questionsList, question1, question2, question3, question4, question5,
            question6, question7
        )

        return questionsList
    }

}