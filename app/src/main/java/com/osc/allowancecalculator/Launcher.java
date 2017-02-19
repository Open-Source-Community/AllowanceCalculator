package com.osc.allowancecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Calendar;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Launcher.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, 3000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar Date = Calendar.getInstance();
        final int year = Date.get(Calendar.YEAR);
        final int month = Date.get(Calendar.MONTH);
        final int day = Date.get(Calendar.DAY_OF_MONTH);
        SharedPreferences date = getSharedPreferences("date", MODE_PRIVATE);
        int lastyear = date.getInt("year", 0);
        int lastmonth = date.getInt("month", 0);
        int lastday = date.getInt("day", 0);
        if (lastday != 0) {
            Calendar today = Calendar.getInstance();
            today.set(year, month, day);
            Calendar endDate = Calendar.getInstance();
            endDate.set(lastyear, lastmonth, lastday);
            long difference = today.getTimeInMillis() - endDate.getTimeInMillis();
            long diffenceInDays = difference / (24 * 60 * 60 * 1000);
            SharedPreferences moneyData = getSharedPreferences(SharedPreferencesUtils.MONEY_DATA_PREFERENCE_FILE_NAME, MODE_PRIVATE);
            long numberOfDays = moneyData.getLong(SharedPreferencesUtils.NUMBER_OF_DAYS_KEY, -1);
            long newNumberOfDays = numberOfDays - diffenceInDays;
            SharedPreferences.Editor moneydataEditor = moneyData.edit();
            moneydataEditor.putLong(SharedPreferencesUtils.NUMBER_OF_DAYS_KEY, newNumberOfDays);
            moneydataEditor.apply();
            SharedPreferences.Editor dateEditor = date.edit();
            dateEditor.putInt("year", year);
            dateEditor.putInt("month", month);
            dateEditor.putInt("day", day);
            dateEditor.apply();
        }
    }
}
