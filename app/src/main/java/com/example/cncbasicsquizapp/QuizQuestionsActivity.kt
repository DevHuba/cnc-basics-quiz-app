package com.example.cncbasicsquizapp

import android.annotation.SuppressLint
import android.content.Intent
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
    private var userName: String? = null

    //Count of total correct answers
    private var globalCorrectAnswers: Int = 0

    //Concrete correct answer
    private var gCorrectAnswer: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting up correct progressBar color
        binding.progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#8be375"))

        //Get all questions
        globalQuestionsList = Constants.qetQuestions()

        //Set user name from intent with constants
        userName = intent.getStringExtra(Constants.USER_NAME)

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
            selectedOptionStyle(binding.tvAnswer1, 1)
            isAnswerPressed = true
            changeSubmitClickable(isAnswerPressed)

        }
        binding.tvAnswer2.setOnClickListener {
            selectedOptionStyle(binding.tvAnswer2, 2)
            isAnswerPressed = true
            changeSubmitClickable(isAnswerPressed)

        }
        binding.tvAnswer3.setOnClickListener {
            selectedOptionStyle(binding.tvAnswer3, 3)
            isAnswerPressed = true
            changeSubmitClickable(isAnswerPressed)

        }
        binding.tvAnswer4.setOnClickListener {
            selectedOptionStyle(binding.tvAnswer4, 4)
            isAnswerPressed = true
            changeSubmitClickable(isAnswerPressed)

        }

        //Submit button slick logic
        binding.btnSubmit.setOnClickListener {
            if (isAnswerPressed && globalSelectedAnswer == 0) {
                //After submit removing ability of clicking to answers
                removeClickOnAnswers(true)
                //If this is first question
                if (globalSelectedAnswer == 0) {
                    questionCounter++
                    //Check for all used questions
                    when {
                        questionCounter <= globalQuestionsList!!.size -> {
                            //Set random questions after first
                            //Take random number from mutable set
                            val randomQuestionAfterFirst = scopeForRandomQuestions.random()
                            //Set random question
                            setQuestion(randomQuestionAfterFirst)
                            //Remove picked random number from mutable set of numbers
                            scopeForRandomQuestions.remove(randomQuestionAfterFirst)
                            //After every new question mute button
                            changeSubmitClickable(false)

                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, globalCorrectAnswers)
                            intent.putExtra(
                                Constants.TOTAL_QUESTIONS, globalQuestionsList!!.size
                            )
                            startActivity(intent)
                            finish()

                        }
                    }
                }


            } else {
                //If not first question
                if (isAnswerPressed) {
                    //Set question from array list
                    val question = globalQuestionsList?.get(questionCounter - 1)

                    //Code that does not depend on correct or wrong answer
                    if (question != null) {
                        answerView(gCorrectAnswer, R.drawable.correct_option_border_bg)
                        correctAnswerStyle(gCorrectAnswer,R.drawable.correct_option_border_bg)
                        changeSubmitClickable(true)
                    }

                    //If wrong answer
                    if (gCorrectAnswer != globalSelectedAnswer) {

                        //Setting selected answer with wrong style
                        answerView(globalSelectedAnswer, R.drawable.wrong_option_border_bg)

                        changeSubmitClickable(true)

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
                        }, 100)
                    }

                    //If correct answer
                    else {
                        globalCorrectAnswers++

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
                        }, 100)
                    }

                    //If last question
                    if (questionCounter == globalQuestionsList!!.size) {
                        binding.btnSubmit.text = getString(R.string.btn_finish)
                    }

//                  If not last question
                    else {
                        if (question?.correctAnswer != globalCorrectAnswers) {
                            binding.btnSubmit.text = getString(R.string.btn_submit_two)
                        } else {
                            binding.btnSubmit.text = getString(R.string.btn_submit_one)
                        }
                    }
                    globalSelectedAnswer = 0
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(randomQuestion: Int) {

        //Set default options
        defaultOptions()

        //Set random question
        val question: Question = globalQuestionsList!![randomQuestion]

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
            "$questionCounter" + "/" + "${globalQuestionsList!!.size}"

        //Array for question prefix
        val prefixArray: ArrayList<String> = arrayListOf(
            "Укажи ка", "Какое будет", "Чикни что за",
            "Напомни ка", "Тыкни", "Выбери", "Тычкани", "Клацани"
        )

        //Setting up first question
        //Chose random prefix
        val randomPrefix = prefixArray[(0 until prefixArray.size).random()]
        //Set random prefix and question
        binding.tvQuestion.text = "$randomPrefix ${question.question}"
        //Set question image
        binding.ivQuestion.setImageResource(question.image)

        //Set random answers
        val answersForQuestion = arrayListOf(
            question.answerOne,
            question.answerTwo,
            question.answerThree,
            question.answerFour
        )
        //Add numbers with fixed scope into mutable set for random answers
        val scopeForRandomAnswers = (0 until answersForQuestion.size).toMutableSet()

        //Loop 4 times ( 4 variants of answers )
        var i = 0
        while (i < 4) {
            i++
            //Take random number from scope
            val randomAnswer = scopeForRandomAnswers.random()
            when (i) {
                1 -> {
                    //Fill text view using random string
                    binding.tvAnswer1.text = answersForQuestion[randomAnswer]
                    //Remove used number from scope
                    scopeForRandomAnswers.remove(randomAnswer)
                    //Check fro correct answer
                    if (binding.tvAnswer1.text == question.answerOne) {
                        gCorrectAnswer = 1
                    }
                }
                2 -> {
                    binding.tvAnswer2.text = answersForQuestion[randomAnswer]
                    scopeForRandomAnswers.remove(randomAnswer)
                    if (binding.tvAnswer2.text == question.answerOne) {
                        gCorrectAnswer = 2
                    }
                }
                3 -> {
                    binding.tvAnswer3.text = answersForQuestion[randomAnswer]
                    scopeForRandomAnswers.remove(randomAnswer)
                    if (binding.tvAnswer3.text == question.answerOne) {
                        gCorrectAnswer = 3
                    }
                }
                4 -> {
                    binding.tvAnswer4.text = answersForQuestion[randomAnswer]
                    scopeForRandomAnswers.remove(randomAnswer)
                    if (binding.tvAnswer4.text == question.answerOne) {
                        gCorrectAnswer = 4
                    }
                }
                else -> {
                    Toast.makeText(this, "Error in for each for answers", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    //Default style options for all answers
    private fun defaultOptions() {

        //Create array for default options styling
        val options = ArrayList<TextView>()

        //Add Text Views into array
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

        //Style each option in array
        for (i in options) {
            i.setTextColor(Color.parseColor("#e6dfdf"))
            i.typeface = Typeface.DEFAULT
            i.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    //Style for selected answer
    private fun selectedOptionStyle(tv: TextView, selectedOptionNum: Int) {
        defaultOptions()
        globalSelectedAnswer = selectedOptionNum
        tv.setTextColor(Color.parseColor("#f4f3f3"))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    //Style for correct answer
    private fun correctAnswerStyle(answer: Int, drawableView: Int) {
        //Setting style accordingly correct answer
        when (answer) {
            1 -> {
                binding.tvAnswer1.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(ContextCompat.getColor(this,R.color.color_text2))
            }
            2 -> {
                binding.tvAnswer2.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer2.setTextColor(ContextCompat.getColor(this,R.color.color_text2))
            }
            3 -> {
                binding.tvAnswer3.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer3.setTextColor(ContextCompat.getColor(this,R.color.color_text2))
            }
            4 -> {
                binding.tvAnswer4.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer4.setTextColor(ContextCompat.getColor(this,R.color.color_text2))
            }
            else -> {
                Toast.makeText(this, "Error on answers painting", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Change style of answers after submit button was pressed
    private fun answerView(answer: Int, drawableView: Int) {
        //Remove clickable from Text Views of answers
        removeClickOnAnswers(false)

        //Setting style accordingly users provided info
        when (answer) {
            1 -> {
                binding.tvAnswer1.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer1.setTextColor(ContextCompat.getColor(this,R.color.color_text))
            }
            2 -> {
                binding.tvAnswer2.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer2.setTextColor(ContextCompat.getColor(this,R.color.color_text))
            }
            3 -> {
                binding.tvAnswer3.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer3.setTextColor(ContextCompat.getColor(this,R.color.color_text))
            }
            4 -> {
                binding.tvAnswer4.background = ContextCompat.getDrawable(this, drawableView)
                binding.tvAnswer4.setTextColor(ContextCompat.getColor(this,R.color.color_text))
            }
            else -> {
                Toast.makeText(this, "Error on answers painting", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Method that remove clickable from text views
    private fun removeClickOnAnswers(clickable: Boolean) {
        //Change button state to clickable in answer view
        isAnswerPressed = true
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

    private fun changeSubmitClickable(makeClickable: Boolean) {
        binding.btnSubmit.isClickable = makeClickable
    }


}













