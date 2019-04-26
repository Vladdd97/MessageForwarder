package com.mobile.messageforwarder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mobile.messageforwarder.util.NumberUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText numberEditText = findViewById(R.id.numberEditText);
        numberEditText.setText(NumberUtil.getFromNumbers());

    }

    public void onClick_saveButton(View v){
        EditText numberEditText = findViewById(R.id.numberEditText);
        String fromNumbers = numberEditText.getText().toString();
        NumberUtil.saveFromNumbers(fromNumbers);
    }

    public void onClick_editNumberButton(View v){
        EditText numberEditText = findViewById(R.id.numberEditText);
        numberEditText.setText(NumberUtil.getFromNumbers());
        String fromNumbers = numberEditText.getText().toString();
        NumberUtil.saveFromNumbers(fromNumbers);

    }
}