package com.example.assignment2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditExpense extends AppCompatActivity {

    private DatePickerDialog datePickerDialogue;
    private Button dateButton;
    private EditText label;
    private EditText cost;
    private EditText reason;
    private EditText notes;
    private Button confirmButton;
    private Button cancelButton;

    private ImageButton deleteButton;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_expense);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        Expense clickedExpense = intent.getParcelableExtra("expense");
        date = clickedExpense.getDate();  // = not defined

        initDatePicker();
        dateButton = findViewById(R.id.date_picker_button_id);
        label = findViewById(R.id.name_edit_text_id);
        cost = findViewById(R.id.cost_edit_text_id);
        reason = findViewById(R.id.reason_edit_text_id);
        notes = findViewById(R.id.notes_edit_text_id);

        confirmButton = findViewById(R.id.confirm_button_id);
        cancelButton = findViewById(R.id.cancel_button_id);
        deleteButton = findViewById(R.id.delete_button_id);

        label.setText(clickedExpense.getLabel());
        cost.setText(clickedExpense.getCostStringWithoutDollarSign());
        reason.setText(clickedExpense.getReason());
        notes.setText(clickedExpense.getNotes());
        dateButton.setText(date.getString());

        confirmButton.setOnClickListener(v -> {
            Expense newExpense = new Expense(label, date, cost, reason, notes);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("expense", newExpense);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            setResult(RESULT_FIRST_USER, resultIntent);
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


  /*  private Date todaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(day, month, year);
    }*/


    public void openDatePickerPopup(View view) {
        datePickerDialogue.show();
    }
}