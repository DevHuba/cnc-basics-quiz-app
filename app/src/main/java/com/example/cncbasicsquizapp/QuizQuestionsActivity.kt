package com.example.cncbasicsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
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

        val question: Question = questionList[0]

        //Check variable for questions to appear only once
        val unpickedQuestions = (0..questionList.size).toMutableSet()
//        val pickedQuestion = (0..questionList.size).toMutableList()

        //Setting up progress bar
        var counter = 1
        progressBar.max = questionList.size
        progressBar.progress = counter
        tvProgressCount.text = "$counter/${questionList.size}"

        btnSubmit.setOnClickListener {
            val randomNumber = (0..questionList.size).random()
            if (unpickedQuestions.isEmpty()) {
                Toast.makeText(this, "All questions already appeared", Toast.LENGTH_LONG).show()
                counter = 0
            }
            println(randomNumber)
            //Update of progress bar
            progressBar.progress = counter
            tvProgressCount.text = "$counter/${questionList.size}"
            println(unpickedQuestions)
            unpickedQuestions.remove(randomNumber)
            println(unpickedQuestions)
            counter++
            println(counter)
        }

    }
}