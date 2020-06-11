package com.example.mybroadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.content.ContextCompat.startActivity

class SMSReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent?.action)) {
            val bundle = intent?.getExtras()
            val message = parseMessage(bundle)

            if (message.size > 0) {
                val contents = message[0]?.messageBody.toString()

                sendToActivity(context, contents)
            }
        }
    }

    fun sendToActivity(context:Context?, str: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", str)
        context?.let {
            it.startActivity(intent)
        }
    }

    fun parseMessage(bundle: Bundle?): Array<SmsMessage?> {
        val objs = bundle?.get("pdus") as Array<Any>
        val messages = arrayOfNulls<SmsMessage>(objs.size)

        for( i in 0 until messages.size) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle?.getString("format")
                messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray, format)
            }
            else {
                messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
            }
        }
        return messages
    }

}