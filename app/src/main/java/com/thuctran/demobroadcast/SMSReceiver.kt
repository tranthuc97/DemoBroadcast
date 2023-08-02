package com.thuctran.demobroadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SMSReceiver : BroadcastReceiver() {
    var TAG:String = SMSReceiver::class.java.name
    val ACTION_SMS:String = "android.provider.Telephony.SMS.RECEVED"    //ACTION nhận SMS, cái này được khai báo trong cả AndroidManifest

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.action!! == ACTION_SMS)
            handleSMS(context!!,intent.extras)        //lấy được nd tin nhắn
    }

    private fun handleSMS(context:Context,dataSMS: Bundle?) {
        var smsPackage:Array<Any> = dataSMS!!.get("pdus") as Array<Any>    //khai báo mảng ds sms
        var text:java.lang.StringBuilder?=null
        var phone:String = ""
        var date:Long = 0

        for (smsItem:Any in smsPackage){
            var sms = SmsMessage.createFromPdu(smsItem as ByteArray)        //lấy được nd tin nhắ, sđt, ngày giờ..
            phone = sms.displayOriginatingAddress
            date = sms.timestampMillis
            text?.append(sms.messageBody)
        }

        var df = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
        var dateText:String = df.format(Date(date)) //format thời gian
        Toast.makeText(context,"phone: $phone \n dateText: $dateText \n\n text: $text",Toast.LENGTH_SHORT).show()
        Log.i(TAG,"tin nhắn đến: $text")
    }
}