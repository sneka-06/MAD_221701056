package com.example.calc

import kotlin.math.*

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentInput: String = ""
    private var operator: String? = null
    private var firstOperand: Double? = null
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
    }

    fun onClick(view: android.view.View) {
        val button = view as Button
        val text = button.text.toString()

        when (text) {
            "=" -> {
                if (firstOperand != null && operator != null && currentInput.isNotEmpty()) {
                    val secondOperand = currentInput.toDouble()
                    val result = when (operator) {
                        "+" -> firstOperand!! + secondOperand
                        "-" -> firstOperand!! - secondOperand
                        "*" -> firstOperand!! * secondOperand
                        "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else Double.NaN
                        "mod" -> firstOperand!! % secondOperand
                        "pow" -> firstOperand!!.pow(secondOperand)
                        else -> 0.0
                    }

                    if (result.isNaN()) {
                        textView.text = "Error"
                        resetCalculator()
                    } else {
                        textView.text = result.toString()
                        currentInput = result.toString()
                        firstOperand = null
                        operator = null
                    }
                }
            }

            "C" -> resetCalculator()

            "sin", "cos", "tan", "sqrt", "log" -> {
                if (currentInput.isNotEmpty()) {
                    val value = currentInput.toDouble()
                    val result = when (text) {
                        "sin" -> sin(Math.toRadians(value))
                        "cos" -> cos(Math.toRadians(value))
                        "tan" -> tan(Math.toRadians(value))
                        "sqrt" -> sqrt(value)
                        "log" -> ln(value) // natural log (ln). If you want log base 10, use log10(value)
                        else -> 0.0
                    }
                    textView.text = result.toString()
                    currentInput = result.toString()
                }
            }

            in "0".."9" -> {
                currentInput += text
                textView.text = currentInput
            }

            in listOf("+", "-", "*", "/", "mod", "pow") -> {
                if (currentInput.isNotEmpty()) {
                    firstOperand = currentInput.toDouble()
                    operator = text
                    currentInput = ""
                }
            }
        }
    }


    private fun resetCalculator() {
        currentInput = ""
        operator = null
        firstOperand = null
        textView.text = "0"
    }
}