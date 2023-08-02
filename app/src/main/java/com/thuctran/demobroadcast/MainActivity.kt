package com.thuctran.demobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    // khai báo textToSpeech chuyển văn bản thành giọng nói
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }


    private fun initViews() {
        requestSMSPermission()

        //chuyển văn bản thành giọng nói
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            fun onInIt(status: Int) {
                if (status != TextToSpeech.ERROR) tts!!.language = Locale.UK
            }
        })

        //tạo BroadcastReceiver
        var receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent!!.action!!.equals(Intent.ACTION_SCREEN_ON)) {
                    sayHello()
                } else if (intent.action!!.equals(Intent.ACTION_SCREEN_OFF)) {
                    sayGoodBye()
                }
            }
        }

        //đối tượng lắng nghe và lọc các ACTION
        var filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)

        registerReceiver(
            receiver,
            filter
        )       //đăng kí lắng nghe với bộ filter được định nghĩa ở trên
    }

    private fun requestSMSPermission() {
        if(checkSelfPermission(android.Manifest.permission.RECEIVE_SMS)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(
                android.Manifest.permission.RECEIVE_SMS
            ),105)
        }
    }

    private fun sayGoodBye() {
        tts!!.speak("Thức đẹp trai", TextToSpeech.QUEUE_FLUSH, null,null)
    }

    private fun sayHello() {
        tts!!.speak("Thức ngon dai", TextToSpeech.QUEUE_FLUSH, null, null)
    }
}