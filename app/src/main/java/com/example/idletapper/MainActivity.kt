package com.example.idletapper

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Log.VERBOSE
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")

    val TAG = "mainActivity"

    var tapCount = 0
    var tapPower = 1 //amount tapCount increments when tap button is pressed
    var idlePower = 0 //amount tapCount increments every second
    var powerUpgradeCost = 10
    var idleUpgradeCost = 10

    val handler = Handler()

    fun idleWait() {
        handler.postDelayed(::idleTapping, 1000)
    }

    fun idleTapping() {
        val tapCountDisplay : TextView = findViewById(R.id.tapCounter)
        tapCount += idlePower
        tapCountDisplay.text = "$tapCount TAPS"
        Log.v(TAG, "idle tapping")
        idleWait()
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pointers to the text fields
        val tapCountDisplay : TextView = findViewById(R.id.tapCounter)
        val tapPowerDisplay : TextView = findViewById(R.id.tapPowerText)
        val idlePowerDisplay : TextView = findViewById(R.id.idlePowerText)

        val tapButton : Button = findViewById(R.id.tapButton)
        tapButton.setOnClickListener {
            tapCount += tapPower
            tapCountDisplay.text = "$tapCount TAPS"
        }
        val powerUpgradeButton : Button = findViewById(R.id.tapUpgrade)
        powerUpgradeButton.setOnClickListener {
            //spend taps to upgrade tapPower if the user has enough
            if (tapCount >= powerUpgradeCost) {
                tapCount -= powerUpgradeCost
                tapPower += 1
                tapPowerDisplay.text = "Tap Power: $tapPower"
                tapCountDisplay.text = "$tapCount TAPS"
            }
        }
        val idleUpgradeButton : Button = findViewById(R.id.idleUpgrade)
        idleUpgradeButton.setOnClickListener {
            //spend taps to upgrade idleTap if the user has enough
            if (tapCount >= idleUpgradeCost) {
                tapCount -= idleUpgradeCost
                idlePower += 1
                idlePowerDisplay.text = "Idle Power : $idlePower"
                tapCountDisplay.text = "$tapCount TAPS"
            }
        }
        //TODO Add code to increment tapCount by idleTap every second
        //TODO Prevent Variable reset on device orientation change

        idleWait()
    }
}