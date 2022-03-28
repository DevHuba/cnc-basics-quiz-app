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

class QuizQuestionsActivity : AppCompatActivity(){

    private var currentQuestion: Int = 1
    private var globalQuestionsList:ArrayList<Question>? = null
    private var globalSelectedAnswer:Int = 0

    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get all questions
        globalQuestionsList = Constants.qetQuestions()

        //Set first question on start
        setQuestion(binding)

//        val pickedQuestion = (0..questionList.size).toMutableList()

        //Setting up progress bar
        binding.progressBar.max = globalQuestionsList!!.size
        binding.progressBar.progress = currentQuestion
        binding.tvQuestionCounter.text = "$currentQuestion/${globalQuestionsList!!.size}"

        //Change option style when its clicked
        binding.tvAnswer1.setOnClickListener{
            selectedOption(binding.tvAnswer1,1,binding)
        }
        binding.tvAnswer2.setOnClickListener{
            selectedOption(binding.tvAnswer2,2,binding)
        }
        binding.tvAnswer3.setOnClickListener{
            selectedOption(binding.tvAnswer3,3,binding)
        }
        binding.tvAnswer4.setOnClickListener{
            selectedOption(binding.tvAnswer4,4,binding)
        }

        //Submit button slick logic
        binding.btnSubmit.setOnClickListener {
            selectedOption(binding.tvAnswer4,4,binding)

            //Image joke logic
            binding.imgJoke.setImageResource(R.drawable.prikalivajewsja)
            binding.imgJoke.visibility = View.VISIBLE
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.imgJoke.visibility = View.GONE
            }, 2000)

            if (globalSelectedAnswer == 0) {
                currentQuestion++
                //Check for all used questions
                when{
                    currentQuestion <= globalQuestionsList!!.size -> {
                        setQuestion(binding)
                    }
                }
            }else{
                val question = globalQuestionsList?.get(currentQuestion - 1)
                if (question?.correctAnswer != globalSelectedAnswer) {
                    answerView(globalSelectedAnswer, R.drawable.wrong_option_border_bg)
                } 
            }




            //My solution


//            val randomNumber = (0..questionList.size).random()
//            if (unpickedQuestions.isEmpty()) {
//                Toast.makeText(this, "All questions already appeared", Toast.LENGTH_LONG).show()
//                currentPosition = 0
//            }
//            println(randomNumber)
//            //Update of progress bar
//            progressBar.progress = currentPosition
//            tvProgressCount.text = "$currentPosition/${questionList.size}"
//
//            //Assign answers
//            tvOptionOne.text = question.optionOne
//            tvOptionTwo.text = question.optionTwo
//            tvOptionThree.text = question.optionThree
//            tvOptionFour.text = question.optionFour
//
//            println(unpickedQuestions)
//            unpickedQuestions.remove(randomNumber)
//            println(unpickedQuestions)
//            currentPosition++
//            println(currentPosition)
        }

    }

    private fun setQuestion (binding: ActivityQuizQuestionsBinding) {
        //Set up first question
        val question: Question = globalQuestionsList!![currentQuestion - 1]
        //Check variable for questions to appear only once
        val unpickedQuestions = (0..globalQuestionsList!!.size).toMutableSet()

        //Setting up first question
        binding.tvQuestion.text = question.question
        binding.ivQuestion.setImageResource(question.image)
        binding.tvAnswer1.text = question.answerOne
        binding.tvAnswer2.text = question.answerTwo
        binding.tvAnswer3.text = question.answerThree
        binding.tvAnswer4.text = question.answerFour


        if (currentQuestion == globalQuestionsList!!.size) {
            binding.btnSubmit.text = "Finish"
        }else {
            binding.btnSubmit.text = "Submit"
        }
    }

    //Default style options for all answers
    private fun defaultOptions (binding: ActivityQuizQuestionsBinding) {
        val options = ArrayList<TextView>()
        binding.tvAnswer1.let {
            options.add(0,it)
        }
        binding.tvAnswer2.let {
            options.add(1,it)
        }
        binding.tvAnswer3.let {
            options.add(2,it)
        }
        binding.tvAnswer4.let {
            options.add(3,it)
        }
        
        for (i in options) {
            i.setTextColor(Color.parseColor("#f4f3f3"))
            i.typeface = Typeface.DEFAULT
            i.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }
    }

    //Style for selected option
    private fun selectedOption (tv:TextView,selectedOptionNum:Int,binding: ActivityQuizQuestionsBinding) {
        defaultOptions(binding)
        globalSelectedAnswer = selectedOptionNum
        tv.setTextColor(Color.parseColor("#f4f3f3"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }
    
    private fun answerView (answer: Int, drawableView: Int) {
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
                Toast.makeText(this,"Error on answers painting",Toast.LENGTH_SHORT).show()
            }
        }
                
    }
}