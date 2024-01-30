package com.example.service;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public double value1 = 0;
    public double value2 = 0;
    public String operation = "";
    private StringBuilder valueString = new StringBuilder(16);
    private TextView display;
    private TextView formula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.tv1);
        formula = findViewById(R.id.tvFormula);
        formula.setText("");
        display.setText("");
    }

    private void updateText(String strToAdd)
    {
        String oldStr = display.getText().toString();
        valueString.append(strToAdd);
        display.setText(valueString);

        formula.append(strToAdd);
    }

    public void one(View view) {
        updateText("1");
    }
    public void two(View view) {
        updateText("2");
    }
    public void three(View view) {
        updateText("3");
    }
    public void four(View view) {
        updateText("4");
    }
    public void five(View view) {
        updateText("5");
    }
    public void six(View view) {
        updateText("6");
    }
    public void seven(View view) {
        updateText("7");
    }
    public void eight(View view) {
        updateText("8");
    }
    public void nine(View view) {
        updateText("9");
    }
    public void zero(View view) {
        updateText("0");
    }
    public void point(View view) {
        updateText(".");
    }

    public void clear(View view) {
        display.setText("");
        formula.setText("");
        valueString.delete(0,16);
        value1 = 0;
        value2 = 0;
        operation = "";
    }

    public void negative(View view) {
        if (display.getText() == "")
        {
            updateText("-");
        }
        else {
            Double negative = Double.parseDouble(String.valueOf(display.getText())) * -1;
            display.setText(negative.toString());
            formula.setText(negative.toString());
        }
    }

    public void percent(View view) {
        if (display.getText() != "") {
            Double percent = Double.parseDouble(String.valueOf(display.getText())) / 100;
            display.setText(percent.toString());
            formula.setText(percent.toString());
        }
    }

   public void Add(View view) {
       value1 = Double.valueOf(String.valueOf(display.getText()));
       operation = "add";
       display.setText("");
       if (formula.getText().toString().contains("=")) {
           formula.setText(String.valueOf(value1));
       }
       formula.append(" + ");
       valueString.delete(0, 16);
       Log.d("value1", String.valueOf(value1));
   }
    public void Subtract(View view) {
        value1 = Double.valueOf(String.valueOf(display.getText()));
        operation = "subtract";
        display.setText("");
        if (formula.getText().toString().contains("=")) {
            formula.setText(String.valueOf(value1));
        }
        formula.append(" - ");
        valueString.delete(0, 16);
        Log.d("value1", String.valueOf(value1));
    }
    public void Multiply(View view) {
        value1 = Double.valueOf(String.valueOf(display.getText()));
        operation = "multiply";
        display.setText("");
        if (formula.getText().toString().contains("=")) {
            formula.setText(String.valueOf(value1));
        }
        formula.append(" * ");
        valueString.delete(0, 16);
        Log.d("value1", String.valueOf(value1));
    }
    public void Divide(View view) {
        value1 = Double.valueOf(String.valueOf(display.getText()));
        operation = "divide";
        display.setText("");
        if (formula.getText().toString().contains("=")) {
            formula.setText(String.valueOf(value1));
        }
        formula.append(" / ");
        valueString.delete(0, 16);
        Log.d("value1", String.valueOf(value1));
    }


    public void Equals(View view) {
        value2 = Double.valueOf(String.valueOf(display.getText()));
        valueString.delete(0,16);
        formula.append(" =");
        Log.d("value1", String.valueOf(value2));
        Intent serviceIntent = new Intent(this, Service.class);
        serviceIntent.putExtra("value1", value1);
        serviceIntent.putExtra("operation", operation);
        serviceIntent.putExtra("value2", value2);
        setResult(MainActivity.RESULT_OK, serviceIntent);
        Log.e("Service", "service is running");
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("my-answer"));
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double answer = intent.getDoubleExtra("answer", 0);
            Log.d("This is it", String.valueOf(answer));
            value1 = answer;
            display.setText(String.valueOf(answer));
        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onPause();
    }


}