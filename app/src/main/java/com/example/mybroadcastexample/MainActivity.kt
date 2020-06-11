package com.example.mybroadcastexample

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var br:BroadcastReceiver
    lateinit var filter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent = getIntent()
        processedIntent(intent)

        button.setOnClickListener {
            Intent().also { intent ->
                intent.action = "com.example.mybroadcast.MY_NOTIFICATION"
                intent.putExtra("data", "Notice me senpai!")
                sendBroadcast(intent)
            }
        }
    }

    override fun onResume() {
        br = TestReciver()
        filter = IntentFilter().apply {
            addAction("com.example.mybroadcast.MY_NOTIFICATION")
        }
        registerReceiver(br, filter)
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(br)
        super.onPause()
    }


    fun processedIntent(intent:Intent?) {
        val str = intent?.getStringExtra("data")
        editText.setText(str) // text 에 넣을땐 editable type ?
    }

    //앱이 종료되지 않은 상태에서 메시지를 받을경우 Receiver 에서 startActivity에 의해 onNewIntent가 실행됨.
    override fun onNewIntent(intent: Intent?) {
        processedIntent(intent)
        super.onNewIntent(intent)
    }

}
