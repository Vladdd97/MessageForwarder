package com.mobile.messageforwarder.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;

public class MFBroadcastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("MFBroadcastReceiver", "enter onReceive() with intentAction=[" + intent.getAction() + "]");
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                boolean isFromNumbers = false;
                List<String> fromNumbers = NumberUtil.getNumbers(NumberType.FROM_NUMBER);
                String from = smsMessage.getOriginatingAddress();
                String message = "Message is redirected from: "+from + ".\nMessage body: " + smsMessage.getMessageBody();
                Log.i("MFBroadcastReceiver", "received message=[" + smsMessage.getMessageBody() + "], from=[" + from + "]");


                for (String fromNumber : fromNumbers) {
                    if (fromNumber.equals(from)) {
                        isFromNumbers = true;
                        break;
                    }
                }

                if (isFromNumbers) {
                    Log.i("MFBroadcastReceiver", "********** sending received message **********");
                    List<String> toNumbers = NumberUtil.getNumbers(NumberType.TO_NUMBER);

                    for(String toNumber : toNumbers){
                        SmsManager.getDefault().sendTextMessage(toNumber, null, message, null, null);
                        Log.i("MFBroadcastReceiver", "send message=[" + message + "], to=[" + toNumber + "]");
                    }

                }

            }
        }
    }
}