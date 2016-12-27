package com.osc.allowancecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import static com.osc.allowancecalculator.SharedPreferencesUtils.ONE_DAY_KEY;
import static com.osc.allowancecalculator.SharedPreferencesUtils.TOTAL_MONEY_KEY;

public class HomeActvity extends AppCompatActivity {
    float oneDayMoney;
    long numberOfDays;
    float totalMoney;
    TextView moneyValueTextView;
    TextView totalMoneyTextView;
    TextView dayMoneyTextView;

    SharedPreferences moneyData;
    SharedPreferences.Editor moneyDataEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moneyData = getSharedPreferences(SharedPreferencesUtils.MONEY_DATA_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        totalMoney = moneyData.getFloat(SharedPreferencesUtils.TOTAL_MONEY_KEY, -1);
        numberOfDays = moneyData.getLong(SharedPreferencesUtils.NUMBER_OF_DAYS_KEY, -1);
        oneDayMoney = totalMoney / numberOfDays;
        moneyDataEditor = moneyData.edit();
        moneyDataEditor.putFloat(SharedPreferencesUtils.ONE_DAY_KEY, oneDayMoney);
        moneyDataEditor.apply();
        if (totalMoney == -1 && numberOfDays == -1) {
            Intent newComerIntent = new Intent(HomeActvity.this, NewComer.class);
            startActivity(newComerIntent);
            finish();
        }

        totalMoneyTextView = (TextView) findViewById(R.id.total);
        dayMoneyTextView = (TextView) findViewById(R.id.day);
        moneyValueTextView = (TextView) findViewById(R.id.moneyvalue);

        totalMoneyTextView.setText(String.valueOf(totalMoney));
        String result = String.format(Locale.getDefault(), "%.3f", oneDayMoney);
        dayMoneyTextView.setText(result);

        // this condition moved to recalculator function,  we may remove it later
        if (numberOfDays == 0) {
            moneyDataEditor.putFloat(TOTAL_MONEY_KEY, 0);
            moneyDataEditor.putLong(SharedPreferencesUtils.NUMBER_OF_DAYS_KEY, numberOfDays);
            moneyDataEditor.putFloat(ONE_DAY_KEY, 0);
            moneyDataEditor.apply();
        }
    }

    public void ButtonClick(View view) {
        if (moneyValueTextView.getText().toString().equals("0"))
            moneyValueTextView.setText("");
        moneyValueTextView.append(view.getTag().toString());
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
        // automatically handle clicks on the HomeActvity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent newComerIntent = new Intent(this, NewComer.class);
            startActivity(newComerIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Delete(View view) {
        if (!moneyValueTextView.getText().toString().equals("")) {
            moneyValueTextView.setText((moneyValueTextView.getText().toString()).substring(0, (moneyValueTextView.getText().toString()).length() - 1));
        }
    }


    public void Add(View view) {
        if (validateUserInput()) {
            if (numberOfDays != -1) {
                try {
                    totalMoney += Float.parseFloat(moneyValueTextView.getText().toString());
                } catch (NumberFormatException ex) {
                    totalMoney = 0;
                }

                moneyDataEditor.putFloat(TOTAL_MONEY_KEY, totalMoney);
                oneDayMoney += Float.parseFloat(moneyValueTextView.getText().toString());
                moneyDataEditor.putFloat(ONE_DAY_KEY, oneDayMoney);
                moneyDataEditor.apply();
                totalMoneyTextView.setText(String.valueOf(totalMoney));
                String result = String.format(Locale.getDefault(), "%.3f", oneDayMoney);
                dayMoneyTextView.setText(result);
                moneyValueTextView.setText("0");
            }
        }
    }

    public void Sub(View view) {
        if (validateUserInput()) {
            if (numberOfDays != -1) {
                try {
                    totalMoney -= Float.parseFloat(moneyValueTextView.getText().toString());
                } catch (NumberFormatException ex) {
                    totalMoney = 0;
                }

                moneyDataEditor.putFloat(SharedPreferencesUtils.TOTAL_MONEY_KEY, totalMoney);
                oneDayMoney -= Float.parseFloat(moneyValueTextView.getText().toString());
                moneyDataEditor.putFloat(SharedPreferencesUtils.ONE_DAY_KEY, oneDayMoney);
                moneyDataEditor.apply();
                totalMoneyTextView.setText(String.valueOf(totalMoney));
                String result = String.format(Locale.getDefault(), "%.3f", oneDayMoney);
                dayMoneyTextView.setText(result);
                moneyValueTextView.setText("0");
            }
        }
    }

    private boolean validateUserInput() {
        return !(moneyValueTextView.getText().toString().equals("") || moneyValueTextView.getText().toString().equals("."));
    }




}