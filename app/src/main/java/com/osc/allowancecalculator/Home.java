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
    float oneDayMoney;
    long numberOfDays;
    float totalMoney;
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
        totalMoney = moneyData.getFloat("totalmoney", -1);
        numberOfDays = moneyData.getLong("numberofdays", -1);
        oneDayMoney =totalMoney/numberOfDays;
        SharedPreferences.Editor moneyDataEditor=moneyData.edit();
        moneyDataEditor.putFloat("oneday",oneDayMoney);
        moneyDataEditor.commit();
        if(totalMoney==-1&&numberOfDays==-1&&oneDayMoney==-1) {
            Intent newComerIntent =new Intent(Home.this,NewComer.class);
            startActivity(newComerIntent);
        }
        totalMoneyTextView= (TextView) findViewById(R.id.total);
        dayMoneyTextView = (TextView) findViewById(R.id.day);
        moneyvalue = (TextView) findViewById(R.id.moneyvalue);
        totalMoneyTextView.setText(totalMoney + "");
        dayMoneyTextView.setText(oneDayMoney + "");

        // this condition moved to recalculator function,  we may remove it later
        if (numberOfDays == 0) {
            SharedPreferences moneyData2 = getSharedPreferences("moneydata", MODE_PRIVATE);
            SharedPreferences.Editor moneyDataEditor2 = moneyData2.edit();
            moneyDataEditor2.putFloat("totalmoney", 0);
            moneyDataEditor2.putLong("numberofdays", numberOfDays);
            moneyDataEditor2.putFloat("oneday", 0);
            moneyDataEditor2.commit();
        }
    }

    public void ButtonClick(View view) {

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
        if(moneyvalue.getText().toString().equals("")==false){
            moneyvalue.setText((moneyvalue.getText().toString()).substring(0,(moneyvalue.getText().toString()).length()-1));
        }
        else {

        }
    }



    public void Add(View view) {
        if(!moneyvalue.getText().equals("")||!moneyvalue.getText().equals(".")) {
            if (numberOfDays != -1) {
                totalMoney += Float.parseFloat(moneyvalue.getText().toString());
                SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
                SharedPreferences.Editor moneyDataEditor = moneyData.edit();
                moneyDataEditor.putFloat("totalmoney", totalMoney);
                oneDayMoney += Float.parseFloat(moneyvalue.getText().toString());
                moneyDataEditor.putFloat("oneday", oneDayMoney);
                moneyDataEditor.commit();
                totalMoneyTextView.setText(totalMoney + "");
                dayMoneyTextView.setText(oneDayMoney + "");
                moneyvalue.setText("");
            }
        }
    }

    public void Sub(View view) {
        if((!moneyvalue.getText().equals(""))||(!moneyvalue.getText().equals("."))) {
            if (numberOfDays != -1) {
                totalMoney -= Float.parseFloat(moneyvalue.getText().toString());
                SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
                SharedPreferences.Editor moneyDataEditor = moneyData.edit();
                moneyDataEditor.putFloat("totalmoney", totalMoney);
                oneDayMoney -= Float.parseFloat(moneyvalue.getText().toString());
                moneyDataEditor.putFloat("oneday", oneDayMoney);
                moneyDataEditor.commit();
                totalMoneyTextView.setText(totalMoney + "");
                dayMoneyTextView.setText(oneDayMoney + "");
                moneyvalue.setText("");
            }
        }
    }
}