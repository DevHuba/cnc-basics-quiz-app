package com.example.cncbasicsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cncbasicsquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnStart : Button = binding.btnStart
        val etName : EditText = binding.etName


        btnStart.setOnClickListener {
            if (etName.text.isNotEmpty()) {

            }
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show()

        }





    }
}