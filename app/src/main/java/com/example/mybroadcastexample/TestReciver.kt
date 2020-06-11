package com.example.mybroadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class TestReciver: BroadcastReceiver() {

    //intent 에 해당 Reciver 넣어서 sendBroadCast 하면 해당 Reciver를 받을 수 있는 앱이 받고 onReceive 동작을 함
    override fun onReceive(context: Context?, intent: Intent?) {
        var data1 = intent?.getStringExtra("data")
        Toast.makeText(context, data1, Toast.LENGTH_SHORT).show()
    }

}