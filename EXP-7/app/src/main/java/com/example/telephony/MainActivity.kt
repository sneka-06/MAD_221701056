package com.example.telephony

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Edge-to-edge padding for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get an instance of the TelephonyManager
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        // Initialize UI elements
        val btnFetch = findViewById<Button>(R.id.btnFetch)
        val tvDeviceName = findViewById<TextView>(R.id.tvDeviceName)
        val tvPhoneType = findViewById<TextView>(R.id.tvPhoneType)
        val tvNetworkISO = findViewById<TextView>(R.id.tvNetworkISO)
        val tvSimISO = findViewById<TextView>(R.id.tvSimISO)
        val tvIMEI = findViewById<TextView>(R.id.tvIMEI)
        val tvSoftwareVersion = findViewById<TextView>(R.id.tvSoftwareVersion)

        // Set up the fetch button click listener
        btnFetch.setOnClickListener {
            // Check for READ_PHONE_STATE permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE), 1
                )
            } else {
                // Fetch and display telephony info
                fetchTelephonyInfo(telephonyManager, tvDeviceName, tvPhoneType, tvNetworkISO, tvSimISO, tvIMEI, tvSoftwareVersion)
            }
        }
    }

    // Fetch and display the telephony information
    private fun fetchTelephonyInfo(
        telephonyManager: TelephonyManager,
        tvDeviceName: TextView,
        tvPhoneType: TextView,
        tvNetworkISO: TextView,
        tvSimISO: TextView,
        tvIMEI: TextView,
        tvSoftwareVersion: TextView
    ) {
        // Get phone type
        val phoneType = when (telephonyManager.phoneType) {
            TelephonyManager.PHONE_TYPE_GSM -> "GSM"
            TelephonyManager.PHONE_TYPE_CDMA -> "CDMA"
            TelephonyManager.PHONE_TYPE_SIP -> "SIP"
            TelephonyManager.PHONE_TYPE_NONE -> "None"
            else -> "Unknown"
        }

        // Get IMEI number (available only in Android 9 or below unless permission is granted)
        val imei = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED) {
                    telephonyManager.imei ?: "Not available"
                } else {
                    "Permission denied"
                }
            } else {
                "Not supported"
            }
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }

        // Display fetched details
        tvDeviceName.text = "Device Name: ${Build.MODEL}"
        tvPhoneType.text = "Phone Type: $phoneType"
        tvNetworkISO.text = "Network Country ISO: ${telephonyManager.networkCountryIso.uppercase()}"
        tvSimISO.text = "SIM Country ISO: ${telephonyManager.simCountryIso.uppercase()}"
        tvIMEI.text = "IMEI: $imei"
        tvSoftwareVersion.text =
            "Device Software Version: ${telephonyManager.deviceSoftwareVersion ?: "Unavailable"}"
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch telephony info
                findViewById<Button>(R.id.btnFetch).performClick()
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
