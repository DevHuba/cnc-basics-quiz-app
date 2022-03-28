package com.example.cncbasicsquizapp

data class Question(
        val id : Int,
        val question: String,
        val image: Int,
        val answerOne: String,
        val answerTwo: String,
        val answerThree: String,
        val answerFour: String,
        val correctAnswer: Int
)
