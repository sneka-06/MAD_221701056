package com.example.sdcard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Setting up the insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the views
        val firstNameField = findViewById<EditText>(R.id.editTextFirstName)
        val cgpaField = findViewById<EditText>(R.id.editTextCGPA)
        val saveButton = findViewById<Button>(R.id.buttonSave)

        // Set up the Save button click listener
        saveButton.setOnClickListener {
            val firstName = firstNameField.text.toString()
            val cgpa = cgpaField.text.toString()

            // Validate the input fields
            if (firstName.isNotEmpty() && cgpa.isNotEmpty()) {
                // Prepare the data to be saved
                val data = "First Name: $firstName\nCGPA: $cgpa\n\n"

                // Write data to file
                try {
                    val fileOutputStream: FileOutputStream = openFileOutput("user_data.txt", MODE_APPEND)
                    fileOutputStream.write(data.toByteArray())
                    fileOutputStream.close()

                    // Clear the input fields after saving
                    firstNameField.text.clear()
                    cgpaField.text.clear()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
