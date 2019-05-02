package com.mobile.messageforwarder;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.messageforwarder.util.ActivityRequestCode;
import com.mobile.messageforwarder.util.NumberType;
import com.mobile.messageforwarder.util.NumberUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout numberTypeTabLayout;
    private NumberType chosenNumberType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    public void initData() {
        chosenNumberType = NumberType.FROM_NUMBER;
        showAllNumbers(chosenNumberType);

        numberTypeTabLayout = findViewById(R.id.numberTypeTabLayout);
        numberTypeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (numberTypeTabLayout.getSelectedTabPosition() == 0) {
                    chosenNumberType = NumberType.FROM_NUMBER;
                    Log.i("MainActivity", "fromNumbers=[" + NumberUtil.getNumbers(chosenNumberType) + "]");
                } else {
                    chosenNumberType = NumberType.TO_NUMBER;
                    Log.i("MainActivity", "toNumbers=[" + NumberUtil.getNumbers(chosenNumberType) + "]");
                }
                showAllNumbers(chosenNumberType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode.ADD_NUMBER_ACTIVITY_CODE) {
            showAllNumbers(chosenNumberType);
        }
    }


    public void onClick_addButton(View v) {
        Intent addNumberIntent = new Intent(MainActivity.this, AddNumberActivity.class);
        addNumberIntent.putExtra(NumberType.class.getName(), chosenNumberType.toString());
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
        Log.i("MainActivity", "selectedNumber=[" + number + "]");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_delete_number);
        TextView popupTextView = dialog.findViewById(R.id.popupTextView);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button deleteButton = dialog.findViewById(R.id.deleteButton);


        popupTextView.setText("Do you wanna delete " + chosenNumberType + " [" + number + "]?");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "Cancel button on popup was clicked");
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberUtil.deleteNumber(chosenNumberType, number);
                Log.i("MainActivity", "deletedNumber=[" + number + "], numberType=[" + NumberType.FROM_NUMBER.toString() + "]");
                dialog.dismiss();
                Toast.makeText(getBaseContext(),"Number [" + number + "] of type ["
                        + chosenNumberType.toString() + "] was deleted.",Toast.LENGTH_LONG).show();

                showAllNumbers(chosenNumberType);
            }
        });

        dialog.show();
    }


}