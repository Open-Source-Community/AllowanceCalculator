package com.osc.allowancecalculator;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences moneyData=getSharedPreferences("moneydata",MODE_PRIVATE);
        float totalMoney=moneyData.getFloat("totalmoney",0.0f);
        long numberOfDays=moneyData.getLong("numberofdays",0);
        float oneDayMoney=moneyData.getFloat("oneday",0.0f);
        TextView totalMoneyTextView=(TextView) findViewById(R.id.total);
        totalMoneyTextView.setText(totalMoney+"");
        TextView oneDayTextView=(TextView) findViewById(R.id.one);
        oneDayTextView.setText(oneDayMoney+"");
        if(numberOfDays>=7)
        {
            TextView sevenDayTextView=(TextView) findViewById(R.id.seven);
            sevenDayTextView.setText(oneDayMoney*7+"");
        }
        if(numberOfDays>=30)
        {
            TextView monthMoneyTextView=(TextView)findViewById(R.id.month);
            monthMoneyTextView.setText(oneDayMoney*30+"");
        }
        final toan.android.floatingactionmenu.FloatingActionButton add=(toan.android.floatingactionmenu.FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog addDialog=new Dialog(Home.this);
                addDialog.setContentView(R.layout.adddialog);
                addDialog.show();
                Button approve=(Button) addDialog.findViewById(R.id.approv);
                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences moneyData=getSharedPreferences("moneydata",MODE_PRIVATE);
                        float totalMoney=moneyData.getFloat("totalmoney",0.0f);
                        long numberOfDays=moneyData.getLong("numberofdays",0);
                        float oneDayMoney=moneyData.getFloat("oneday",0.0f);
                        EditText addMoneyEditText =(EditText) addDialog.findViewById(R.id.addmoney);
                        float addMoney=Float.parseFloat(addMoneyEditText.getText().toString());
                        SharedPreferences.Editor moneyDataEditor=moneyData.edit();
                        moneyDataEditor.putFloat("totalmoney",addMoney+totalMoney);
                        moneyDataEditor.putFloat("oneday",addMoney+oneDayMoney);
                        moneyDataEditor.commit();
                        totalMoney=moneyData.getFloat("totalmoney",0.0f);
                        numberOfDays=moneyData.getLong("numberofdays",0);
                        oneDayMoney=moneyData.getFloat("oneday",0.0f);
                        TextView totalMoneyTextView=(TextView) findViewById(R.id.total);
                        totalMoneyTextView.setText(totalMoney+"");
                        TextView oneDayTextView=(TextView) findViewById(R.id.one);
                        oneDayTextView.setText(oneDayMoney+"");
                        if(numberOfDays>=7)
                        {
                            TextView sevenDayTextView=(TextView) findViewById(R.id.seven);
                            sevenDayTextView.setText(oneDayMoney*7+"");
                        }
                        if(numberOfDays>=30)
                        {
                            TextView monthMoneyTextView=(TextView)findViewById(R.id.month);
                            monthMoneyTextView.setText(oneDayMoney*30+"");
                        }
                        addDialog.dismiss();
                    }
                });
                Button cancel=(Button) addDialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDialog.dismiss();
                    }
                });
            }
        });
        toan.android.floatingactionmenu.FloatingActionButton sub=(toan.android.floatingactionmenu.FloatingActionButton) findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog subDialog=new Dialog(Home.this);
                subDialog.setContentView(R.layout.subdialog);
                subDialog.show();
                Button approve=(Button) subDialog.findViewById(R.id.approv);
                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences moneyData=getSharedPreferences("moneydata",MODE_PRIVATE);
                        float totalMoney=moneyData.getFloat("totalmoney",0.0f);
                        long numberOfDays=moneyData.getLong("numberofdays",0);
                        float oneDayMoney=moneyData.getFloat("oneday",0.0f);
                        EditText addMoneyEditText =(EditText) subDialog.findViewById(R.id.submoney);
                        float addMoney=Float.parseFloat(addMoneyEditText.getText().toString());
                        SharedPreferences.Editor moneyDataEditor=moneyData.edit();
                        moneyDataEditor.putFloat("totalmoney",totalMoney-addMoney);
                        moneyDataEditor.putFloat("oneday",oneDayMoney-addMoney);
                        moneyDataEditor.commit();
                        totalMoney=moneyData.getFloat("totalmoney",0.0f);
                        numberOfDays=moneyData.getLong("numberofdays",0);
                        oneDayMoney=moneyData.getFloat("oneday",0.0f);
                        TextView totalMoneyTextView=(TextView) findViewById(R.id.total);
                        totalMoneyTextView.setText(totalMoney+"");
                        TextView oneDayTextView=(TextView) findViewById(R.id.one);
                        oneDayTextView.setText(oneDayMoney+"");
                        if(numberOfDays>=7)
                        {
                            TextView sevenDayTextView=(TextView) findViewById(R.id.seven);
                            sevenDayTextView.setText(oneDayMoney*7+"");
                        }
                        if(numberOfDays>=30)
                        {
                            TextView monthMoneyTextView=(TextView)findViewById(R.id.month);
                            monthMoneyTextView.setText(oneDayMoney*30+"");
                        }
                        subDialog.dismiss();
                    }
                });
                Button cancel=(Button) subDialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subDialog.dismiss();
                    }
                });
             }
        });
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
}
