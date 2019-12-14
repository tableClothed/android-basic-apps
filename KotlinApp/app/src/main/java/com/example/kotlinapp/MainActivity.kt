package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var count = 0

    fun plusOne(view : View) {
        count = 0
        numTextView.setText(count.toString())
    }

    fun resetButton(vie : View) {
        count++
        numTextView.setText(count.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.numTextView)
        textView.text = "Hello Fam!"
    }
}
