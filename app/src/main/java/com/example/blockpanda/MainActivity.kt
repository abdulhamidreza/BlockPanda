package com.example.blockpanda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), View.OnClickListener {

    // declaring objects of Button class
    private var start: Button? = null
    private var stop: Button? = null
    private var usages: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById<View>(R.id.startButton) as Button
        stop = findViewById<View>(R.id.stopButton) as Button
        usages = findViewById<View>(R.id.usagesButton) as Button

        // declaring listeners for the
        // buttons to make them respond
        // correctly according to the process
        start!!.setOnClickListener(this)
        stop!!.setOnClickListener(this)
        usages!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        // process to be performed
        // if start button is clicked
        if (view === start) {
            // starting the service
            startService()
        }

        // process to be performed
        // if stop button is clicked
        else if (view === stop) {
            // stopping the service
            stopService()
        }
        else if (view === usages) {
            callUsageStatsActivity()
        }
    }

    fun startService() {
        val serviceIntent = Intent(this, LockService::class.java)
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService() {
        val serviceIntent = Intent(this, LockService::class.java)
        stopService(serviceIntent)
    }

    private fun callUsageStatsActivity() {
        val lockIntent = Intent(this, UsageStatsActivity::class.java)
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(lockIntent)
    }

}

