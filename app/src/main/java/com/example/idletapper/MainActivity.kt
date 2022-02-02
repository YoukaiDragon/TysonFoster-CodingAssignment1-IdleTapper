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

    //call idleTapping() 1 second later
    fun idleWait() {
        handler.postDelayed(::idleTapping, 1000)
    }

    //passively increment the tapCount based on idlePower
    fun idleTapping() {
        val tapCountDisplay : TextView = findViewById(R.id.tapCounter)
        tapCount += idlePower
        tapCountDisplay.text = "$tapCount TAPS"
        Log.v(TAG, "idle tapping")
        idleWait() //run this function again 1 second later
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pointers to the text fields
        val tapCountDisplay : TextView = findViewById(R.id.tapCounter)
        val tapPowerDisplay : TextView = findViewById(R.id.tapPowerText)
        val idlePowerDisplay : TextView = findViewById(R.id.idlePowerText)

        //load the saved instance state if one exists
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                tapCount = getInt(STATE_TAPS)
                tapPower = getInt(STATE_TAPPOW)
                idlePower = getInt(STATE_IDLEPOW)
                powerUpgradeCost = getInt(STATE_POWUP)
                idleUpgradeCost = getInt(STATE_IDLEUP)
            }

            //set teh text displays with the loaded values
            tapCountDisplay.text = "$tapCount TAPS"
            tapPowerDisplay.text = "Tap Power: $tapPower"
            idlePowerDisplay.text = "Idle Power: $idlePower"
        }

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
        //TODO Prevent Variable reset on device orientation change

        idleWait()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putInt(STATE_TAPS, tapCount)
            putInt(STATE_TAPPOW, tapPower)
            putInt(STATE_IDLEPOW, idlePower)
            putInt(STATE_POWUP, powerUpgradeCost)
            putInt(STATE_IDLEUP, idleUpgradeCost)
        }

    }

    companion object {
        val STATE_TAPS = "tapCount"
        val STATE_TAPPOW = "tapPower"
        val STATE_IDLEPOW = "idlePower"
        val STATE_POWUP = "powerUpgradeCost"
        val STATE_IDLEUP = "idleUpgradeCost"
    }
}