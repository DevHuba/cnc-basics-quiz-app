package com.example.cncbasicsquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cncbasicsquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Take data from intent
        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuests = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 1)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,1)

        //Show user score
        binding.tvScore.text = "Правильных $correctAnswers/$totalQuests Всего"

        //Emojis with string representation of an unicode character
        val emojiEducation: String = getEmoji(0x1F393)
        val emojiParty: String = getEmoji(0x1F389)
        val emojiGoblin: String = getEmoji(0x1F47A)
        val emojiAnger: String = getEmoji(0x1F525)
        val emojiThumbsUp: String = getEmoji(0x1F44D)

        //Check for MORE than 70%
        when {
            correctAnswers >= totalQuests * 0.7 -> {
                //Set name and text into info view
                binding.tvInfo.text =
                    "$emojiParty Отличная работа $userName $emojiParty\n" +
                            "$emojiEducation Теперь можно готовиться к устной $emojiEducation"
                binding.ivResultBottom.setImageResource(R.drawable.finish2)
                binding.ivResultOnTop.visibility = View.GONE

            }
            //Check for MORE than 50%
            correctAnswers >= totalQuests * 0.5 -> {
                binding.tvInfo.text =
                    "$emojiThumbsUp Неплохо $userName $emojiThumbsUp\n" +
                            "Ты можешь лучше, повнимательней..."
                binding.ivResultBottom.setImageResource(R.drawable.finish)
                binding.ivResultOnTop.visibility = View.GONE

            }
            //Check for LESS than 50%
            correctAnswers < totalQuests / 2 -> {
                binding.tvInfo.text = "$emojiGoblin $userName $emojiGoblin\n" +
                        "$emojiAnger БООЛЬШЕ ПРАКТИКИ $emojiAnger\n" +
                        " $emojiAnger $emojiAnger БООООЛЬШЕ $emojiAnger $emojiAnger"
                binding.ivResultBottom.setImageResource(R.drawable.brain)
                binding.ivResultOnTop.setImageResource(R.drawable.failed)

            }
        }

        binding.btnFinishQuiz.setOnClickListener {
            //Back to quiz start
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    //Represents unicode into string emoji
    private fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

}