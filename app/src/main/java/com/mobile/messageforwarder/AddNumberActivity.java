package com.mobile.messageforwarder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobile.messageforwarder.util.NumberType;
import com.mobile.messageforwarder.util.NumberUtil;

import java.util.Arrays;

public class AddNumberActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        initData();
    }

    public void onClick_saveButton(View v) {
        EditText numberEditText = findViewById(R.id.numberEditText);
        NumberType numberType = NumberType.valueOf(spinner.getSelectedItem().toString());
        NumberUtil.saveNumber(numberType, numberEditText.getText().toString());
        Log.i("MFBroadcastReceiver", "savedNumber=[" + numberEditText.getText().toString() + "], numberType=[" + numberType.toString() + "]");
        finish();
    }

    public void onClick_backButton(View v) {
        Log.i("MFBroadcastReceiver", "back button was clicked");
        finish();
    }

    public void initData() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddNumberActivity.this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(NumberType.TO_NUMBER.toString(), NumberType.FROM_NUMBER.toString()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}
