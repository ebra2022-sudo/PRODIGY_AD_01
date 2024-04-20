package com.example.androidcalculator

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidcalculator.my_personal_android_functionalities.evaluate


// calculator view model class
class Calculator: ViewModel() {
    // state variable for input expression
    var inputExpression by mutableStateOf("")
    // flag variable to change the state when the equal button is pressed
    private var _flag by mutableStateOf(false)
    val flag: Boolean
        get() = _flag
    // called when the number buttons are pressed and append to the input expression
    fun receiveNumber(char: String) {
        _flag = false
        Log.d("append", "$char Expression Value:${inputExpression}")
        if (char in "0123456789") inputExpression += char
        else if(char in "+-×÷") {
            if (inputExpression.isNotEmpty()) {
                val lastChar = inputExpression.last()
                // if last char is an operator, replace it with the new operator
                if (lastChar in "+-×÷") inputExpression = inputExpression.dropLast(1)
            }
            inputExpression += char
        }
        else if(char == ".") {
            if (inputExpression.isNotEmpty()) {
                val lastChar = inputExpression.last()
                if (lastChar!='.') {
                    // if last char is an operator, and the current char is a dot, add a zero before the dot
                    if (lastChar in "+-×÷") inputExpression += "0"
                    inputExpression += char
                }
            }
        }
        else if(char =="("){
            if (inputExpression.isNotEmpty()) {
                val lastChar = inputExpression.last()
                // if last char is not a operator, add a multiplication operator before the parenthesis
                if (lastChar !in "+-×÷") inputExpression += "×"
            }
            inputExpression += char

        }
        else if(char ==")") inputExpression += char
    }
    // called when equal button is pressed
    fun onEqual() {
        inputExpression = evaluate()
        _flag = true
    }
    // called when clear button is pressed
    fun clear() {
        inputExpression = ""
    }
    // called when dl button is pressed
    fun delete() {
        inputExpression = inputExpression.dropLast(1)
    }
    // return the string of result of the input expression if it is syntactically correct else empty string
    fun evaluate(): String {
        return  try {
            evaluate(inputExpression).toString()
        } catch (e: Exception) {
            ""
        }
    }
}