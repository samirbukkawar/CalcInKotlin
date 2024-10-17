package com.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView : TextView
    private lateinit var outputTextview : TextView

    private var input: String = ""
    private var opera1: Double = 0.0
    private var opera2: Double = 0.0
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        outputTextview = findViewById(R.id.output)

        val buttons = listOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.button_clear),
            findViewById(R.id.button_para2),
            findViewById(R.id.button_percent),
            findViewById(R.id.button_divide),
            findViewById(R.id.button_multiplication),
            findViewById(R.id.button_sub),
            findViewById(R.id.button_add),
            findViewById(R.id.button_equal),
            findViewById(R.id.button_dot)
        )

        buttons.forEach{
            buttons ->
            buttons.setOnClickListener(View.OnClickListener {
                handleButtonClick(buttons.text.toString())
            })
        }

    }

    private fun appendInput(value:String){
        input+=value
        inputTextView.text = input
    }

    private fun appendDecimal(){
        if(!input.contains('.')){
            input+="."
            inputTextView.text = input
        }
    }

    private fun handleOperator(op:String){
        operator = op
        opera1 = input.toDouble()
        input = ""
        inputTextView.text = ""
    }

    private fun calculateResult(){
        if(input.isNotEmpty()){
            opera2 = input.toDouble()

            val result = when(operator){
                "+" -> opera1 + opera2
                "-" -> opera1 - opera2
                "X" -> opera1 * opera2
                "/" -> "%.2f".format(opera1 / opera2)
                else -> throw IllegalStateException("Invalid Operator")
            }

            outputTextview.text = result.toString()
            input = result.toString()
            inputTextView.text = input
        }
    }

    private fun clearInput(){
        input = ""
        opera1 = 0.0
        opera2 = 0.0
        operator = ""
        inputTextView.text = ""
        outputTextview.text = ""
    }

    private fun handlePercentage(){
        if(input.isNotEmpty()){
            var value = input.toDouble()/100
            input = value.toString()
            inputTextView.text = input
        }
    }

    private fun toggleSign(){
        if(input.isNotEmpty() && input != "0"){
            var value = input.toDouble() * -1
            input = value.toString()
            inputTextView.text = input
        }
    }

    private fun String.isNumeric():Boolean {
        return try{
            this.toDouble()
            return true
        }catch (n : NumberFormatException){
            return false
        }
    }

    private fun handleButtonClick(value: String){
        try{

            when{
                value.isNumeric() -> appendInput(value)
                value in setOf("+", "-", "X", "/") -> handleOperator(value)
                value == "=" -> calculateResult()
                value == "AC" -> clearInput()
                value == (".") -> appendDecimal()
                value == ("+/-") -> toggleSign()
                value == ("%") -> handlePercentage()
            }
        }catch (e:Exception){
            Toast.makeText(this, "Invalid Argument", Toast.LENGTH_SHORT).show()
            println("Invalid Argument")
        }
    }
}