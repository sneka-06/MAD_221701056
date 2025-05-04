package com.example.sms

import android.os.Bundle
import android.telephony.SmsManager
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
        val phoneNumberEditText = findViewById<EditText>(R.id.editTextPhnNumber)
        val messageEditText = findViewById<EditText>(R.id.editTextMessage)
        val sendMessageButton = findViewById<Button>(R.id.buttonSendMessage)

        sendMessageButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val message = messageEditText.text.toString()

            if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
                sendSms(phoneNumber, message)
            } else {
                Toast.makeText(this, "Please enter a phone number and message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send message: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}