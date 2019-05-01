package com.mobile.messageforwarder;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.messageforwarder.util.ActivityRequestCode;
import com.mobile.messageforwarder.util.NumberType;
import com.mobile.messageforwarder.util.NumberUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button fromNumbersButton;
    private Button toNumbersButton;

    private NumberType chosenNumberType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        //NumberUtil.deleteAllNumbers();
//        NumberUtil.saveFromNumber("fromNumber_1" + NumberUtil.SEPARATOR + "fromNumber_2");
//        NumberUtil.saveToNumber("toNumber_1" + NumberUtil.SEPARATOR + "toNumber_2");
    }

    public void initData() {
        fromNumbersButton = findViewById(R.id.fromNumbersButton);
        toNumbersButton = findViewById(R.id.toNumbersButton);

        chooseNumberType(NumberType.TO_NUMBER);
        showAllNumbers(chosenNumberType);
    }

    public void chooseNumberType(NumberType numberType) {

        chosenNumberType = numberType;
        if (numberType.equals(NumberType.FROM_NUMBER)) {
            fromNumbersButton.setBackgroundColor(getResources().getColor(R.color.enabledButton));
            toNumbersButton.setBackgroundColor(getResources().getColor(R.color.disabledButton));
        } else {
            fromNumbersButton.setBackgroundColor(getResources().getColor(R.color.disabledButton));
            toNumbersButton.setBackgroundColor(getResources().getColor(R.color.enabledButton));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.ADD_NUMBER_ACTIVITY_CODE) {
            showAllNumbers(chosenNumberType);
        }
    }

    public void onClick_fromNumbersButton(View v) {
        chooseNumberType(NumberType.FROM_NUMBER);
        showAllNumbers(chosenNumberType);

        Log.i("MFBroadcastReceiver", "fromNumbers=[" + NumberUtil.getNumbers(chosenNumberType) + "]");
        showAllNumbers(chosenNumberType);
    }


    public void onClick_toNumbersButton(View v) {
        chooseNumberType(NumberType.TO_NUMBER);
        showAllNumbers(chosenNumberType);

        Log.i("MFBroadcastReceiver", "fromNumbers=[" + NumberUtil.getNumbers(NumberType.TO_NUMBER) + "]");
        showAllNumbers(NumberType.TO_NUMBER);

    }

    public void onClick_addButton(View v) {
        Intent addNumberIntent = new Intent(MainActivity.this, AddNumberActivity.class);
        startActivityForResult(addNumberIntent, ActivityRequestCode.ADD_NUMBER_ACTIVITY_CODE);
    }

    public void showAllNumbers(NumberType numberType) {
        ListView numbersListView = findViewById(R.id.numbersListView);
        List<String> numbers = NumberUtil.getNumbers(numberType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, numbers);
        numbersListView.setAdapter(adapter);

        // set onClick event for every item of list
        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(((TextView) view).getText().toString());
            }
        });

    }

    public void showPopup(final String number) {
        Log.i("MFBroadcastReceiver", "selectedNumber=[" + number + "]");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_delete_number);
        TextView popupTextView = dialog.findViewById(R.id.popupTextView);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button deleteButton = dialog.findViewById(R.id.deleteButton);


        popupTextView.setText("Do you wanna delete " + chosenNumberType + " [" + number + "]?");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MFBroadcastReceiver", "Cancel button on popup was clicked");
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberUtil.deleteNumber(chosenNumberType, number);
                Log.i("MFBroadcastReceiver", "deletedNumber=[" + number + "], numberType=[" + NumberType.FROM_NUMBER.toString() + "]");
                dialog.dismiss();
                showAllNumbers(chosenNumberType);
            }
        });

        dialog.show();
    }


}