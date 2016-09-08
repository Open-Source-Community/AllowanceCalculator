package com.osc.allowancecalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;

public class Launcher extends AppCompatActivity {
    long oneDayMoney;
    long numberOfDays;
    long totalMoney;
    int days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);


        Calendar Date = Calendar.getInstance();
         int year=Date.get(Calendar.YEAR);
         int month=Date.get(Calendar.MONTH);
         int day=Date.get(Calendar.DAY_OF_MONTH);
        String lastDate = moneyData.getString("date", "-1");
        if(lastDate.equals("-1")==false){
            String date=day+"/"+month+"/"+year;
            char[] last=lastDate.toCharArray();
            if(!lastDate.equals(date)) {
                int lastday=last[0];
                int lastmonth=last[2];
                int lastyear=last[4];
                Calendar lastdateCalendar=Calendar.getInstance();
                lastdateCalendar.set(lastyear,lastmonth,lastday);
                Date.set(year,month,day);
                long difference=lastdateCalendar.getTimeInMillis()-Date.getTimeInMillis();
                days= (int) (difference/ (24 * 60 * 60 * 1000));
                days++;
                recalculate();
            }
        }


        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent homeIntent=new Intent(Launcher.this,Home.class);
               startActivity(homeIntent);
               finish();
           }
       },3000);

    }



    public void recalculate(){
       SharedPreferences moneyData=getSharedPreferences("moneydata",MODE_PRIVATE);
        totalMoney = moneyData.getLong("totalmoney", 0);
        numberOfDays = moneyData.getLong("numberofdays", -1);
        oneDayMoney = moneyData.getLong("oneday", 0);
        numberOfDays-=days;
        if(totalMoney<0)
            return;
        else if(numberOfDays==0){
            AlertDialog.Builder fineDialog = new AlertDialog.Builder(Launcher.this);
            fineDialog.setMessage("الايام خلصت وباقى معاك" + totalMoney);
            fineDialog.show();
            SharedPreferences moneyData2 = getSharedPreferences("moneydata", MODE_PRIVATE);
            SharedPreferences.Editor moneyDataEditor = moneyData2.edit();
            moneyDataEditor.putLong("totalmoney", 0);
            moneyDataEditor.putLong("numberofdays", numberOfDays);
            moneyDataEditor.putLong("oneday", 0);
            moneyDataEditor.commit();
        }

        oneDayMoney=totalMoney/numberOfDays;
        SharedPreferences.Editor moneyDataEditor = moneyData.edit();
        moneyDataEditor.putLong("totalmoney",  totalMoney);
        moneyDataEditor.putLong("numberofdays", numberOfDays);
        moneyDataEditor.putLong("oneday", (totalMoney / numberOfDays));
        moneyDataEditor.commit();

    }
}
