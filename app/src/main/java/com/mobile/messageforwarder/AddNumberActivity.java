package com.mobile.messageforwarder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.messageforwarder.util.NumberType;
import com.mobile.messageforwarder.util.NumberUtil;


public class AddNumberActivity extends AppCompatActivity {

    private TextView numberTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        initData();
    }

    public void onClick_saveButton(View v) {
        EditText numberEditText = findViewById(R.id.numberEditText);
        NumberType numberType = NumberType.valueOf(numberTypeTextView.getText().toString());
        NumberUtil.saveNumber(numberType, numberEditText.getText().toString());
        Log.i("AddNumberActivity", "savedNumber=[" + numberEditText.getText().toString() + "], numberType=[" + numberType.toString() + "]");
        Toast.makeText(getBaseContext(),"Number [" + numberEditText.getText().toString() + "] of type ["
                + numberType.toString() + "] was saved.",Toast.LENGTH_LONG).show();
        finish();
    }


    public void initData() {
        Intent intent = getIntent();
        numberTypeTextView = findViewById(R.id.numberTypeTextView);
        numberTypeTextView.setText(intent.getExtras().getString(NumberType.class.getName()));

    }

}
