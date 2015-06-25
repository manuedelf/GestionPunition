package com.manue.gestionpunition.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.manue.gestionpunition.R;
import com.manue.gestionpunition.asynchronous.PunitionCreation;
import com.manue.gestionpunition.model.Punition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPunitionActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText name;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private Button button;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_punition_grid);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        findViewsById();
        setDateTimeField();
    }

    public void addPunition(View button) {
        Punition punition = new Punition();
        punition.childId = getIntent().getLongExtra("id", -1);
        punition.name = name.getText().toString();
        try {
            punition.begin = dateFormatter.parse(fromDateEtxt.getText().toString());
            punition.end = dateFormatter.parse(toDateEtxt.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new PunitionCreation(AddPunitionActivity.this).execute(punition);

    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);

        name = (EditText) findViewById(R.id.EditTextName);
        name.setText(null);
        name.requestFocus();
        button = (Button) findViewById(R.id.button2);
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);


    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        fromDateEtxt.setOnFocusChangeListener(this);
        toDateEtxt.setOnFocusChangeListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                toDateEtxt.requestFocus();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                button.requestFocus();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == fromDateEtxt && hasFocus) {
            fromDatePickerDialog.show();
        } else if (v == toDateEtxt && hasFocus) {
            toDatePickerDialog.show();
        }
    }

}
