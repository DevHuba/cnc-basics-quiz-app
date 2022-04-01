package com.example.cncbasicsquizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cncbasicsquizapp.databinding.ActivityResultBinding
import java.util.*

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Take data from intent
        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuests = intent.getStringExtra(Constants.TOTAL_QUESTIONS)?.toInt()
        val correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)?.toInt()

        //Show user score
        binding.tvScore.text = "You`r score is $correctAnswers / $totalQuests"


        //Check for null safety
        if (correctAnswers != null && totalQuests != null) {
            //Check for MORE than 70%
            if (correctAnswers >= totalQuests * 0.7) {
                //Set name and text into info view
                binding.tvInfo.text = "Great job $userName !"
                binding.ivResult.setImageResource(R.drawable.finish2)

            }
            //Check for MORE than 50%
            else if (correctAnswers >= totalQuests * 0.5) {
                binding.tvInfo.text = "Good result $userName, but you must be more attentive"
                binding.ivResult.setImageResource(R.drawable.finish)

            }
            //Check for LESS than 50%
            else if (correctAnswers < totalQuests / 2) {
                binding.tvInfo.text = "You need more practice $userName"
                binding.ivResult.setImageResource(R.drawable.brain)

            }
        }

    }
}