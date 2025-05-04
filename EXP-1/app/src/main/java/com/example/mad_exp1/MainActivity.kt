package com.example.mad_exp1

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var textView4: TextView
        var buttonChangeSize: Button
        var buttonChangeColor: Button
        var buttonChangeBackground: Button
        // Initialize views
        val mainLayout = findViewById<LinearLayout>(R.id.main)
        textView4 = findViewById<TextView>(R.id.textView4)
        buttonChangeSize = findViewById<Button>(R.id.button)
        buttonChangeColor = findViewById<Button>(R.id.button2)
        buttonChangeBackground = findViewById<Button>(R.id.button3)

        buttonChangeSize.setOnClickListener {
            textView4.textSize = 24f // Set text size to 24sp
        }

        // Change text color when button is clicked
        buttonChangeColor.setOnClickListener {
            textView4.setTextColor(0xFFFF5722.toInt());
        }


        buttonChangeBackground.setOnClickListener {
            mainLayout.setBackgroundColor(0xFF00FFFF.toInt()) ;
        }
    }
}