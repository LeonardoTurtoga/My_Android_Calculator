package com.com_new_calculator.new_calculator_fr

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.com_new_calculator.new_calculator_fr.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.abs




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var solution: TextView
    private lateinit var answer: TextView
    var canAddDecimal = true
    var canAddOperation = false

    private var currentResult: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        solution = binding.solutionTV
        answer = binding.answerTV
    }


    fun numberButtons(view: View) {
        if (view !is Button) return
        when (view.text) {
            "." -> {
                solution.append(view.text)
                canAddDecimal = false
            }
            else -> {
                solution.append(view.text)
            }
        }
        canAddOperation = true
        animateTextSize(solution,  32f,42f)
        animateTextSize(answer, 64f,32f)
        calculate()
    }

    fun allClearButton(view: View) {
        solution.text = ""
        answer.text = ""
        animateTextSize(solution,  32f,42f)
        animateTextSize(answer, 64f,32f)
    }

    fun backSpaceButton(view: View) {
        var length = solution.length()
        if (length > 0) {
            solution.text = solution.text.subSequence(0, length - 1)
        }
        calculate()
    }

    fun operationButtons(view: View) {

        if (view is Button && canAddOperation) {
            if(currentResult != null){
                solution.text = currentResult.toString()
                solution.append(view.text)
                answer.text = ""
                animateTextSize(solution,  32f,42f)
                animateTextSize(answer, 64f,32f)
                currentResult = null
            }else {
                solution.append(view.text)
                canAddOperation = false
                canAddDecimal = true
            }
        }
    }

    fun equalsButton(view: View) {
        try {
          val  result = evaluateMathExpression(solution.text.toString())
            val CurrentValue = result.toString()
            currentResult = result
            answer.text = CurrentValue
            animateTextSize(solution, 42f, 32f)
            animateTextSize(answer, 32f, 64f)
            canAddOperation = true
            canAddDecimal = true
        } catch (e: IllegalArgumentException) {
            solution.text = "Error"
            answer.text = ""
        }
    }

    fun evaluateMathExpression(input: Any): Double {
        val validOperators = setOf('+', '-', '*', '%','/' )
        val expressionText = when (input) {
            is String -> input
            is Button -> input.text.toString()
            else -> throw IllegalArgumentException("Input must be String or Button")
        }
        val sanitized = expressionText
            .replace("x", "*", ignoreCase = true)
            .replace("÷", "/", ignoreCase = true)
        val operatorCount = sanitized.count { it in validOperators }
        val hasValidStructure = try {
            ExpressionBuilder(sanitized).build()
            true
        } catch (e: Exception) {
            false
        }
        when {
            operatorCount == 0 -> throw IllegalArgumentException("No operators found")
            !hasValidStructure -> throw IllegalArgumentException("Invalid expression structure")
            else -> return ExpressionBuilder(sanitized).build().evaluate()
        }
    }



    fun animateTextSize(textView: TextView, startSize: Float, endSize: Float, duration: Long = 500L
    ): ValueAnimator? {
        val currentSizeSp = textView.textSize / textView.resources.displayMetrics.scaledDensity
        if (abs(currentSizeSp - endSize) < 0.1f) {
            return null
        }
        return ValueAnimator.ofFloat(startSize, endSize).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                textView.textSize = animator.animatedValue as Float
            }
            start()
        }
    }


    fun calculate() {
        try {
            val result = evaluateMathExpression(solution.text.toString())
            answer.text = result.toString()
        } catch (e: Exception) {}
    }
}




