package com.osc.allowancecalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.Set;

public class Home extends AppCompatActivity {
    long oneDayMoney;
    long numberOfDays;
    long totalMoney;
    TextView moneyvalue;
    TextView totalMoneyTextView;
    TextView dayMoneyTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
        totalMoney = moneyData.getLong("totalmoney", 0);
        numberOfDays = moneyData.getLong("numberofdays", -1);
        oneDayMoney = moneyData.getLong("oneday", 0);
        totalMoneyTextView= (TextView) findViewById(R.id.total);
        dayMoneyTextView = (TextView) findViewById(R.id.day);
        totalMoneyTextView.setText(totalMoney + "");
        dayMoneyTextView.setText(oneDayMoney + "");

        // this condition moved to recalculator function,  we may remove it later
        if (numberOfDays == 0) {
            SharedPreferences moneyData2 = getSharedPreferences("moneydata", MODE_PRIVATE);
            SharedPreferences.Editor moneyDataEditor = moneyData2.edit();
            moneyDataEditor.putLong("totalmoney", 0);
            moneyDataEditor.putLong("numberofdays", numberOfDays);
            moneyDataEditor.putLong("oneday", 0);
            moneyDataEditor.commit();
        }
    }

    public void ButtonClick(View view) {

        moneyvalue = (TextView) findViewById(R.id.moneyvalue);
        if(moneyvalue.getText().toString().equals("0"))
            moneyvalue.setText("");
        moneyvalue.append(view.getTag().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent newComerIntent=new Intent(this,NewComer.class);
            startActivity(newComerIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Delete(View view)
    {
        if(moneyvalue.getText().toString().equals("")==false)
        moneyvalue.setText((moneyvalue.getText().toString()).substring(0,(moneyvalue.getText().toString()).length()-1));

    }



    public void Add(View view) {
        if (numberOfDays!=-1) {
            totalMoney += Long.parseLong(moneyvalue.getText().toString());
            SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
            SharedPreferences.Editor moneyDataEditor = moneyData.edit();
            moneyDataEditor.putLong("totalmoney",  totalMoney);
            oneDayMoney+= Long.parseLong(moneyvalue.getText().toString());
            moneyDataEditor.putLong("oneday",oneDayMoney);
            moneyDataEditor.commit();
            totalMoneyTextView.setText(totalMoney + "");
            dayMoneyTextView.setText(oneDayMoney + "");
            moneyvalue.setText("");
        }
    }

    public void Sub(View view) {
        if (numberOfDays!=-1) {
            totalMoney -= Long.parseLong(moneyvalue.getText().toString());
            SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
            SharedPreferences.Editor moneyDataEditor = moneyData.edit();
            moneyDataEditor.putLong("totalmoney", totalMoney);
            oneDayMoney -= Long.parseLong(moneyvalue.getText().toString());
            moneyDataEditor.putLong("oneday", oneDayMoney);
            moneyDataEditor.commit();
            totalMoneyTextView.setText(totalMoney + "");
            dayMoneyTextView.setText(oneDayMoney + "");

        }
        moneyvalue.setText("");
    }
}