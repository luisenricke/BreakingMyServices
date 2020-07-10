package com.luisenricke.breakingmyservices

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {
            ForegroundNotification.startService(this, "Test")
        }

        btn_stop.setOnClickListener {
            ForegroundNotification.stopService(this)
        }
    }
}
