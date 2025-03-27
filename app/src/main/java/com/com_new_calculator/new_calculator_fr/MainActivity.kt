package com.com_new_calculator.new_calculator_fr

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.com_new_calculator.new_calculator_fr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var solution: TextView
    private lateinit var answer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        solution = binding.solutionTV
        answer = binding.answerTV
    }



    fun allClearButton(view: View) {
        solution.text = ""
        answer.text = ""
    }

    fun numberButtons(view: View) {

        if (view is Button) {
            solution.append(view.text)
        }
    }
    fun backSpaceButton(view: View){
    var length = solution.length()

        if(length > 0){
            solution.text = solution.text.subSequence(0,length - 1)
        }
    }
}
