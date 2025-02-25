package com.example.assignment2;

import android.content.Intent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;

public class CreateNewExpense extends AppCompatActivity {

    private DatePickerDialog datePickerDialogue;
    private Button dateButton;
    private EditText label;
    private EditText cost;
    private EditText reason;
    private EditText notes;
    private Button confirmButton;
    private Button cancelButton;
    private Date date = todaysDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_expense);

        initDatePicker();
        dateButton = findViewById(R.id.date_picker_button_id);
        dateButton.setText(date.getString());

        label = findViewById(R.id.name_edit_text_id);
        cost = findViewById(R.id.cost_edit_text_id);
        reason = findViewById(R.id.reason_edit_text_id);
        notes = findViewById(R.id.notes_edit_text_id);

        confirmButton = findViewById(R.id.confirm_button_id);
        cancelButton = findViewById(R.id.cancel_button_id);

        confirmButton.setOnClickListener(v -> {
            Expense expense = new Expense(label, date, cost, reason, notes);
            Intent intent = new Intent(CreateNewExpense.this, MainActivity.class);
            intent.putExtra("expense", expense);
            startActivity(intent);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewExpense.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.day = day;
                date.month = month;
                date.year = year;
                dateButton.setText(date.getString());
            }
        };
        datePickerDialogue = new DatePickerDialog(this, dateSetListener, date.year, date.month, date.day);
    }


    private Date todaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(day, month, year);
    }


    public void openDatePickerPopup(View view) {
        datePickerDialogue.show();
    }
}