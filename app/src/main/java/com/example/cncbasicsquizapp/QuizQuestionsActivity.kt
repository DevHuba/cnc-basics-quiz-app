package com.example.cncbasicsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.example.cncbasicsquizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get all views (not necessary, for more purity)
        val tvDescription = binding.tvQuestion.text.toString()
        val img = binding.ivQuestion
        val progressBar = binding.progressBar
        var tvProgressCount = binding.tvQuestionCounter
        val tvOptionOne = binding.tvAnswer1
        val tvOptionTwo = binding.tvAnswer2
        val tvOptionThree = binding.tvAnswer3
        val tvOptionFour = binding.tvAnswer4
        val btnSubmit = binding.btnSubmit

        //Get all questions
        val questionList = Constants.qetQuestions()

        //Get random question
        val randomNumber = (0..questionList.size).random()
        val question: Question = questionList[randomNumber]

        //Setting up progress bar
        progressBar.max = questionList.size
//        progressBar.progress = currentPosition
//        tvProgressCount.text = "$currentPosition / ${questionList.size}"


    }
}