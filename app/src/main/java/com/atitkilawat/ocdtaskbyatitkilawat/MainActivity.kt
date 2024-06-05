package com.atitkilawat.ocdtaskbyatitkilawat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var inputBox1: TextInputEditText
    private lateinit var inputBox2: TextInputEditText
    private lateinit var inputBox3: TextInputEditText
    private lateinit var calculateBtn: AppCompatButton
    private lateinit var generateBtn: AppCompatButton
    private lateinit var infoTextView: AppCompatTextView
    private lateinit var resultIntersect: AppCompatTextView
    private lateinit var resultUnion: AppCompatTextView
    private lateinit var resultHighest: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUI()
    }

    private fun setUI() {
        //Binding UI
        inputBox1 = findViewById(R.id.input1) //For Input 1
        inputBox2 = findViewById(R.id.input2) //For Input 2
        inputBox3 = findViewById(R.id.input3) //For Input 3

        //Setting EditText to just accept numbers and comma as input
        val dkl = DigitsKeyListener.getInstance("0123456789,")
        inputBox1.keyListener = dkl
        inputBox2.keyListener = dkl
        inputBox3.keyListener = dkl

        infoTextView = findViewById(R.id.info_text)
        resultIntersect = findViewById(R.id.result_text_intersection)
        resultUnion = findViewById(R.id.result_text_union)
        resultHighest = findViewById(R.id.result_text_highest_no)

        generateBtn = findViewById(R.id.generate_random_btn) //Button to generate random inputs
        calculateBtn = findViewById(R.id.calculate_btn) //Button to calculate intersect, union and highest number

        generateBtn.setOnClickListener {
            generateRandom()
        }

        calculateBtn.setOnClickListener {
            if (validateInputs()) {
                calculateResult()
            }
        }
    }

    private fun generateRandom() {
        inputBox1.setText(generateRandomCommaSeparatedNumbers())
        inputBox2.setText(generateRandomCommaSeparatedNumbers())
        inputBox3.setText(generateRandomCommaSeparatedNumbers())
    }

    private fun generateRandomCommaSeparatedNumbers(): String {
        val randomNumbers = List(5) { Random.nextInt(1, 20) }
        return randomNumbers.joinToString(",")
    }

    private fun validateInputs(): Boolean {
        return validateInput(inputBox1) && validateInput(inputBox2) && validateInput(inputBox3)
    }

    private fun validateInput(editText: TextInputEditText): Boolean {
        val input = editText.text.toString()
        val regex = Regex("^\\d+(,\\d+)*$")

        return if (input.isEmpty() || !regex.matches(input)) {
            editText.error = "Invalid input. Please enter a comma-separated list of numbers."
            false
        } else {
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        // Read inputs from all
        val input1 = inputBox1.text.toString()
        val input2 = inputBox2.text.toString()
        val input3 = inputBox3.text.toString()

        // Parsing inputs from String to Integer value
        val list1 = input1.split(",").map { it.trim().toInt() }
        val list2 = input2.split(",").map { it.trim().toInt() }
        val list3 = input3.split(",").map { it.trim().toInt() }

        // Converting to sets
        val set1 = list1.toSet()
        val set2 = list2.toSet()
        val set3 = list3.toSet()

        // Calculating intersection
        val intersection = set1.intersect(set2).intersect(set3)

        // Calculating union
        val union = set1.union(set2).union(set3)

        // Finding the highest number in all 3 sets
        val highestNumber = union.maxOrNull() ?: 0

        //Hide Info Text
        infoTextView.visibility = View.INVISIBLE

        // Display results
        resultIntersect.text = "Intersection: ${intersection.joinToString(", ")}"
        resultUnion.text = "Union: ${union.joinToString(", ")}"
        resultHighest.text = "Highest Number: $highestNumber"
    }
}