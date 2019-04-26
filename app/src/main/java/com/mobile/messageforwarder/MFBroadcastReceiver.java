package com.mobile.messageforwarder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class MFBroadcastReceiver extends BroadcastReceiver {

    private static String SEND_TO_NUMBER = "5556";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("MFBroadcastReceiver", "enter onReceive() with intentAction=[" + intent.getAction()+"]");
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                //TODO: add check for fromNumbers
                String message = smsMessage.getMessageBody();
                String from = smsMessage.getOriginatingAddress();
                Log.i("MFBroadcastReceiver", "received message=[" + message+"], from=["+from+"]");

                //TODO: add check for toNumbers
                SmsManager.getDefault().sendTextMessage(SEND_TO_NUMBER,null,message,null,null);
                Log.i("MFBroadcastReceiver", "send message=[" + message+"], to=["+SEND_TO_NUMBER+"]");
            }
        }
    }
}