package com.osc.allowancecalculator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class NewComer extends AppCompatActivity {
    long numberOfDays = 0;
    SharedPreferences moneyData;
    SharedPreferences.Editor moneyDataEditor;

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
                EditText moneyEditText = (EditText) findViewById(R.id.totalmoney);
                final
                String Digits = "(\\p{Digit}+)";
                final String HexDigits = "(\\p{XDigit}+)";
// an exponent is 'e' or 'E' followed by an optionally
// signed decimal integer.
                final String Exp = "[eE][+-]?" + Digits;
                final String fpRegex = ("[\\x00-\\x20]*" + // Optional leading "whitespace"
                        "[+-]?(" +         // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from the Java Language Specification, 2nd
                        // edition, section 3.10.2.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.(" + Digits + ")(" + Exp + ")?)|" +

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"


                if (!(Pattern.matches(fpRegex, moneyEditText.getText().toString())) || numberOfDays == 0) {
                    Toast.makeText(NewComer.this, "برجاء ادخال المبلغ واليوم", Toast.LENGTH_LONG).show();
                } else {
                    float money = Float.parseFloat(moneyEditText.getText().toString());
                    SharedPreferences moneyData = getSharedPreferences("moneydata", MODE_PRIVATE);
                    SharedPreferences.Editor moneyDataEditor = moneyData.edit();
                    moneyDataEditor.putFloat("totalmoney", money);
                    moneyDataEditor.putLong("numberofdays", numberOfDays);
                    moneyDataEditor.commit();
                    setDateOfLastRecalculation();
                    Intent homeIntent = new Intent(NewComer.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        });
        ImageView calendar = (ImageView) findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar Date = Calendar.getInstance();
                final int year = Date.get(Calendar.YEAR);
                final int month = Date.get(Calendar.MONTH);
                final int day = Date.get(Calendar.DAY_OF_MONTH);
                SharedPreferences date = getSharedPreferences("date", MODE_PRIVATE);
                SharedPreferences.Editor dateEditor = date.edit();
                dateEditor.putInt("year", year);
                dateEditor.putInt("month", month);
                dateEditor.putInt("day", day);
                dateEditor.apply();
                DatePickerDialog datePicker = new DatePickerDialog(NewComer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int endyear, int monthOfYear, int dayOfMonth) {
                        Calendar today = Calendar.getInstance();
                        today.set(year, month, day);
                        Calendar endDate = Calendar.getInstance();
                        endDate.set(endyear, monthOfYear, dayOfMonth);
                        long difference = endDate.getTimeInMillis() - today.getTimeInMillis();
                        numberOfDays = difference / (24 * 60 * 60 * 1000);
                        numberOfDays++;
                        if (numberOfDays == 1) {
                            Toast.makeText(NewComer.this, "يوم", Toast.LENGTH_LONG).show();
                        } else if (numberOfDays == 2) {
                            Toast.makeText(NewComer.this, "يومين", Toast.LENGTH_LONG).show();
                        } else if (numberOfDays >= 3 && numberOfDays <= 10) {
                            Toast.makeText(NewComer.this, numberOfDays + " ايام ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NewComer.this, numberOfDays + " يوم ", Toast.LENGTH_LONG).show();
                        }
                    }
                }, year, month, day);
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
                datePicker.show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setDateOfLastRecalculation()
    {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        moneyData = getSharedPreferences(SharedPreferencesUtils.MONEY_DATA_PREFERENCE_FILE_NAME, MODE_PRIVATE);
        moneyDataEditor = moneyData.edit();
        moneyDataEditor.putString(SharedPreferencesUtils.DATE_OF_LAST_RECALCULATION_KEY, currentDate);
        moneyDataEditor.commit();
    }
}