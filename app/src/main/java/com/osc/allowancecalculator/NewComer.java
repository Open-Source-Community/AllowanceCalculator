package com.osc.allowancecalculator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;

public class NewComer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {

            }
        });
        ImageView calendar=(ImageView) findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar Date = Calendar.getInstance();
                int year=Date.get(Calendar.YEAR);
                int month=Date.get(Calendar.MONTH);
                int day=Date.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker=new DatePickerDialog(NewComer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                },year,month,day);
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
                datePicker.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
