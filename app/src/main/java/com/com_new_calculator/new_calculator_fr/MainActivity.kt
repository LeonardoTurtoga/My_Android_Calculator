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
    var canAddDecimal = true
    var canAddOperation = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        solution = binding.solutionTV
        answer = binding.answerTV
    }


    fun numberButtons(view: View) {

        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal)
                    solution.append(view.text)
                    canAddDecimal = false
            } else
                solution.append(view.text)
                canAddOperation = true
        }
    }
//// from Deepseek
//    fun numberButtons(view: View) {
//        if (view is Button) {
//            when {
//                view.text == "." && canAddDecimal -> {
//                    solution.append(view.text)
//                    canAddDecimal = false
//                }
//                view.text != "." -> {
//                    solution.append(view.text)
//                    canAddOperation =true
//                }
//            }
//        }
//    }

    fun allClearButton(view: View) {
        solution.text = ""
        answer.text = ""
    }

    fun backSpaceButton(view: View){
    var length = solution.length()

        if(length > 0){
            solution.text = solution.text.subSequence(0,length - 1)
        }
    }

    fun operationButtons(view: View) {
        if(view is Button && canAddOperation){
            solution.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }
    fun equalsButton(view: View) {



    }


}

