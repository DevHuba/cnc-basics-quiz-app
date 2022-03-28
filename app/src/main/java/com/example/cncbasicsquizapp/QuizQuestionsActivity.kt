package com.example.cncbasicsquizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cncbasicsquizapp.databinding.ActivityQuizQuestionsBinding


class QuizQuestionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var currentQuestion: Int = 1
    private var globalQuestionsList: ArrayList<Question>? = null
    private var globalSelectedAnswer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get all questions
        globalQuestionsList = Constants.qetQuestions()

        //Set first question on start
        setQuestion()

        //Change option style when its clicked
        binding.tvAnswer1.setOnClickListener {
            selectedOption(binding.tvAnswer1, 1)
        }
        binding.tvAnswer2.setOnClickListener {
            selectedOption(binding.tvAnswer2, 2)
        }
        binding.tvAnswer3.setOnClickListener {
            selectedOption(binding.tvAnswer3, 3)
        }
        binding.tvAnswer4.setOnClickListener {
            selectedOption(binding.tvAnswer4, 4)
        }

        //Submit button slick logic
        binding.btnSubmit.setOnClickListener {
            //If this is first question
            if (globalSelectedAnswer == 0) {
                currentQuestion++
                //Check for all used questions
                when {
                    currentQuestion <= globalQuestionsList!!.size -> {
                        setQuestion()
                    }
                    else -> {
                                        //TODO: Congrats screen logic and intent put here
                        Toast.makeText(this,"Congrats ! Completed Quiz !",Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val question = globalQuestionsList?.get(currentQuestion - 1)
                if (question?.correctAnswer != globalSelectedAnswer) {
                    answerView(globalSelectedAnswer, R.drawable.wrong_option_border_bg)
                }
                if (question != null) {
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                }
                if (currentQuestion == globalQuestionsList!!.size) {
                    binding.btnSubmit.text = getString(R.string.btn_finish)
                } else {
                    binding.btnSubmit.text = getString(R.string.btn_submit)
                }
                globalSelectedAnswer = 0
            }

            //Image joke logic
            binding.imgJoke.setImageResource(R.drawable.prikalivajewsja)
            binding.imgJoke.visibility = View.VISIBLE
            //Joke shows only for 2000 milliseconds
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.imgJoke.visibility = View.GONE
            }, 20)
        }
    }

    //OK //
    private fun setQuestion() {
        //Set up first question with help of current position
        val question: Question = globalQuestionsList!![currentQuestion - 1]

        //Reset all questions
        defaultOptions()

        if (currentQuestion == globalQuestionsList!!.size) {
            binding.btnSubmit.text = getString(R.string.btn_finish)
        } else {
            binding.btnSubmit.text = getString(R.string.btn_after_submit)
        }

        //Setting up progress bar
        binding.progressBar.progress = currentQuestion
        binding.progressBar.max = globalQuestionsList!!.size
        binding.tvQuestionCounter.text =
            "${currentQuestion}" + "/" + "${globalQuestionsList!!.size}"

        //Setting up first question
        binding.tvQuestion.text = question.question
        binding.ivQuestion.setImageResource(question.image)
        binding.tvAnswer1.text = question.answerOne
        binding.tvAnswer2.text = question.answerTwo
        binding.tvAnswer3.text = question.answerThree
        binding.tvAnswer4.text = question.answerFour
    }

    //Default style options for all answers
    private fun defaultOptions() {
        val options = ArrayList<TextView>()
        binding.tvAnswer1.let {
            options.add(0, it)
        }
        binding.tvAnswer2.let {
            options.add(1, it)
        }
        binding.tvAnswer3.let {
            options.add(2, it)
        }
        binding.tvAnswer4.let {
            options.add(3, it)
        }

        for (i in options) {
            i.setTextColor(Color.parseColor("#f4f3f3"))
            i.typeface = Typeface.DEFAULT
            i.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    //Style for selected option
    private fun selectedOption(tv: TextView, selectedOptionNum: Int) {
        defaultOptions()
        globalSelectedAnswer = selectedOptionNum
        tv.setTextColor(Color.parseColor("#f4f3f3"))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvAnswer1.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                binding.tvAnswer2.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                binding.tvAnswer3.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                binding.tvAnswer4.background = ContextCompat.getDrawable(this, drawableView)
            }
            else -> {
                Toast.makeText(this, "Error on answers painting", Toast.LENGTH_SHORT).show()
            }
        }

    }
}













