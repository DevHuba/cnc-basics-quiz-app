package com.example.cncbasicsquizapp

import android.content.res.ColorStateList
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
import java.util.*
import kotlin.collections.ArrayList


class QuizQuestionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var questionCounter: Int = 1
    private var globalQuestionsList: ArrayList<Question>? = null
    private var globalSelectedAnswer: Int = 0
    private var isAnswerPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting up correct progressBar color
        binding.progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#8be375"));

        //Get all questions
        globalQuestionsList = Constants.qetQuestions()

        //Random first question logic
        //Add numbers with fixed scope into mutable set for random questions
        val scopeForRandomQuestions = (0 until globalQuestionsList!!.size).toMutableSet()
        //Take random number from mutable set
        val randomFirstQuestion = scopeForRandomQuestions.random()
        //Set first random question on start
        setQuestion(randomFirstQuestion)
        //Remove picked random number from mutable set of numbers
        scopeForRandomQuestions.remove(randomFirstQuestion)


        //Change option style when its clicked
        binding.tvAnswer1.setOnClickListener {
            selectedOption(binding.tvAnswer1, 1)
            isAnswerPressed = true
            changeClickable(isAnswerPressed)

        }
        binding.tvAnswer2.setOnClickListener {
            selectedOption(binding.tvAnswer2, 2)
            isAnswerPressed = true
            changeClickable(isAnswerPressed)

        }
        binding.tvAnswer3.setOnClickListener {
            selectedOption(binding.tvAnswer3, 3)
            isAnswerPressed = true
            changeClickable(isAnswerPressed)

        }
        binding.tvAnswer4.setOnClickListener {
            selectedOption(binding.tvAnswer4, 4)
            isAnswerPressed = true
            changeClickable(isAnswerPressed)

        }

        //Submit button slick logic
        binding.btnSubmit.setOnClickListener {
            println(isAnswerPressed)
            println(questionCounter)


//            if (isAnswerPressed) {


//                isAnswerPressed = false


            //After submit removing ability of clicking to answers
            removeClickOnAnswers(true)
            //If this is first question
            if (globalSelectedAnswer == 0) {
                questionCounter++
                if (!isAnswerPressed) {
                    changeClickable(false)
                }
//                if (isAnswerPressed || questionCounter > 1) {
                //Check for all used questions
                when {
                    questionCounter <= globalQuestionsList!!.size -> {


//                            binding.btnSubmit.isClickable = isAnswerPressed


                        //Set random questions after first
                        //Take random number from mutable set
                        val randomQuestionAfterFirst = scopeForRandomQuestions.random()
                        //Set random question
                        setQuestion(randomQuestionAfterFirst)
                        //Remove picked random number from mutable set of numbers
                        scopeForRandomQuestions.remove(randomQuestionAfterFirst)


//                            isAnswerPressed = true
                        changeClickable(false)


                    }
                    else -> {
                        //TODO: Congrats screen logic and intent put here
                        Toast.makeText(this, "Congrats ! Completed Quiz !", Toast.LENGTH_SHORT)
                            .show()

                    }
                }
//                }


            } else {
                //If not first question
                if (isAnswerPressed) {
                    changeClickable(true)


                    //Set question from array list
                    val question = globalQuestionsList?.get(questionCounter - 1)
                    //Code that does not depend on correct or wrong answer
                    if (question != null) {
                        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                        changeClickable(true)
                    }
                    //If wrong answer
                    if (question?.correctAnswer != globalSelectedAnswer) {
                        //Setting selected answer with wrong style
                        answerView(globalSelectedAnswer, R.drawable.wrong_option_border_bg)
                        changeClickable(true)

                        //Random images for wrong answer
                        val wrongImageArray = ArrayList<Int>()
                        Collections.addAll(
                            wrongImageArray,
                            R.drawable.prikalivajewsja,
                            R.drawable.sutulij_grustnij,
                            R.drawable.mega_sutulij,
                            R.drawable.sutulij_ustal
                        )
                        val randomForWrongImages = (0 until wrongImageArray.size).random()
                        //Image joke logic for wrong answer
                        binding.imgJoke.setImageResource(wrongImageArray[randomForWrongImages])
                        binding.imgJoke.visibility = View.VISIBLE
                        //Joke shows only for 2000 milliseconds
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            binding.imgJoke.visibility = View.GONE
                        }, 500)

                    }
                    //If correct answer
                    if (question?.correctAnswer == globalSelectedAnswer) {

                        //Random images for correct answer
                        val correctImageArray = ArrayList<Int>()
                        Collections.addAll(
                            correctImageArray,
                            R.drawable.correct_one,
                            R.drawable.correct_two,
                            R.drawable.correct_three,
                            R.drawable.correct_four,
                        )
                        val randomForCorrectImages = (0 until correctImageArray.size).random()
                        //Image joke logic for correct answer
                        binding.imgJoke.setImageResource(correctImageArray[randomForCorrectImages])
                        binding.imgJoke.visibility = View.VISIBLE
                        //Joke shows only for 2000 milliseconds
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            binding.imgJoke.visibility = View.GONE
                        }, 500)

                    }
                    //If last question
                    if (questionCounter == globalQuestionsList!!.size) {
                        binding.btnSubmit.text = getString(R.string.btn_finish)
                    }
                    //If not last question
                    else {
                        binding.btnSubmit.text = getString(R.string.btn_submit)
                    }
                    globalSelectedAnswer = 0


                    isAnswerPressed = false


                }

            }
        }
//        }

    }


    private fun setQuestion(randomQuestion: Int) {
        if (!isAnswerPressed) {
            binding.btnSubmit.isClickable = false
        }

        //Set random question
        val question: Question = globalQuestionsList!![randomQuestion]

        //Set default options
        defaultOptions()

        //Check for end of quiz and is not answerView
        if (questionCounter == globalQuestionsList!!.size && globalSelectedAnswer != 0) {
            binding.btnSubmit.text = getString(R.string.btn_finish)
        } else {
            binding.btnSubmit.text = getString(R.string.btn_after_submit)
        }

        //Setting up progress bar
        binding.progressBar.progress = questionCounter
        binding.progressBar.max = globalQuestionsList!!.size
        binding.tvQuestionCounter.text =
            "${questionCounter}" + "/" + "${globalQuestionsList!!.size}"

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

    //Change style of answers after submit button was pressed
    private fun answerView(answer: Int, drawableView: Int) {
        //Remove clickability from Text Views of answers
        removeClickOnAnswers(false)

        //Setting style accordingly users provided info
        when (answer) {
            1 -> {
                binding.tvAnswer1.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(Color.parseColor("#2B2D42"))
            }
            2 -> {
                binding.tvAnswer2.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(Color.parseColor("#2B2D42"))
            }
            3 -> {
                binding.tvAnswer3.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(Color.parseColor("#2B2D42"))
            }
            4 -> {
                binding.tvAnswer4.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(Color.parseColor("#2B2D42"))
            }
            else -> {
                Toast.makeText(this, "Error on answers painting", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Method that removes clickability from text views
    private fun removeClickOnAnswers(clickable: Boolean) {
        if (clickable) {
            binding.tvAnswer1.isClickable = true
            binding.tvAnswer2.isClickable = true
            binding.tvAnswer3.isClickable = true
            binding.tvAnswer4.isClickable = true
        } else {
            binding.tvAnswer1.isClickable = false
            binding.tvAnswer2.isClickable = false
            binding.tvAnswer3.isClickable = false
            binding.tvAnswer4.isClickable = false
        }
    }

    private fun changeClickable(makeClickable: Boolean) {
        binding.btnSubmit.isClickable = makeClickable
    }

}













