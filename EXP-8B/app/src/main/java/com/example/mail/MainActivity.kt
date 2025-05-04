package com.example.mail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        val emailField = findViewById<EditText>(R.id.editEmail)
        val subjectField = findViewById<EditText>(R.id.editSubject)
        val messageField = findViewById<EditText>(R.id.editMessage)
        val sendButton = findViewById<Button>(R.id.buttonSendEmail)

        sendButton.setOnClickListener {
            val email = emailField.text.toString()
            val subject = subjectField.text.toString()
            val message = messageField.text.toString()

            if (email.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    val uriText = "mailto:${Uri.encode(email)}" +
                            "?subject=${Uri.encode(subject)}" +
                            "&body=${Uri.encode(message)}"

                    val uri = Uri.parse(uriText)

                    data = uri

                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                try {
                    startActivity(intent)
                    Toast.makeText(this, "Opening email app...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}