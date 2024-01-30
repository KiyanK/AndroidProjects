package com.example.service;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.security.Provider;
import java.util.Objects;

public class Service extends android.app.Service {



    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();

        double value1 = intent.getDoubleExtra("value1", 0);
        double value2 = intent.getDoubleExtra("value2", 0);
        Log.d("value1 in service", String.valueOf(value1));
        String operation = intent.getStringExtra("operation");
        Log.d("current operation", operation);
        Double answer = null;
        

        if (operation.equals("add"))
        {
            answer = Double.valueOf(String.valueOf(value1 + value2));
        }
        else if (operation.equals("subtract"))
        {
            answer = Double.valueOf(String.valueOf(value1 - value2));
        }
        else if (operation.equals("multiply"))
        {
            answer = Double.valueOf(String.valueOf(value1 * value2));
        }
        else if (operation.equals("divide"))
        {
            answer = Double.valueOf(String.valueOf(value1 / value2));
        }
        Log.d("Answer", String.valueOf(answer));
        
        sendMessage(answer);
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessage(Double answer) {
        Intent intent = new Intent("my-answer");
        intent.putExtra("answer", answer);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}



