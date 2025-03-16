package com.example.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_EXPENSE = 1;
    private static final int REQUEST_CODE_EDIT_EXPENSE = 2;



    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private TextView total, dailyAverage, monthlyTotal;
    private ImageButton addButton;
    private List<Expense> expenseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton = findViewById(R.id.add_button_id);

        expenseList = PreferenceManager.loadExpenses(this);

        expenseAdapter = new ExpenseAdapter(expenseList);
        recyclerView.setAdapter(expenseAdapter);
        generateRecyclerView();
        updateExpenses();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewExpense.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_EXPENSE);
            }
        });

        expenseAdapter.setOnItemClickListener(new ExpenseAdapter.OnExpenseClickListener() {
            @Override
            public void onExpenseClick(int position) {
                Expense clickedExpense = expenseList.get(position);
                Intent intent = new Intent(MainActivity.this, EditExpense.class);
                intent.putExtra("expense", (Parcelable) clickedExpense);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_EXPENSE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUEST_CODE_ADD_EXPENSE && data != null){
            Expense expense = data.getParcelableExtra("expense");
            addExpense(expense);
        }

        if (requestCode == REQUEST_CODE_EDIT_EXPENSE){
            int position = data.getIntExtra("position", -1);

            if (resultCode == RESULT_OK) {
                Expense expense = data.getParcelableExtra("expense");
                changeExpense(position, expense);

            } else if (resultCode == RESULT_FIRST_USER){
                deleteExpense(position);
            }
        }
    }



    private void generateRecyclerView()
    {
        int size = expenseList.size();
        for(int i = 0; i < size; i++) {
            expenseAdapter.notifyItemInserted(i);
        }
    }



    private void updateExpenses()
    {
        total = findViewById(R.id.total_value_id);
        dailyAverage = findViewById(R.id.daily_avg_id);
        monthlyTotal = findViewById(R.id.monthly_avg_id);

        total.setText(getTotalValueStr());
        dailyAverage.setText(getDailyAverageStr());
        monthlyTotal.setText(getMonthlyAverageStr());
    }



    private void addExpense(Expense expense)
    {
        expenseList.add(expense);
        expenseAdapter.notifyItemInserted(expenseList.size() - 1);
        updateExpenses();
        PreferenceManager.saveExpenses(this, expenseList);
    }



    private void changeExpense(int position, Expense expense)
    {
        expenseList.remove(position);
        expenseList.add(position, expense);
        expenseAdapter.notifyItemChanged(position);
        updateExpenses();
        PreferenceManager.saveExpenses(this, expenseList);
    }



    private void deleteExpense(int position)
    {
        expenseList.remove(position);
        expenseAdapter.notifyItemRemoved(position);
        updateExpenses();
        PreferenceManager.saveExpenses(this, expenseList);
    }



    private String getTotalValueStr()
    {
        if (expenseList.isEmpty()) {
            return "$0";
        }

        int size = expenseList.size();
        float sum = 0;
        for (int i = 0; i < size; i++){
            sum += expenseList.get(i).getCost();
        }

        float result = sum / 100;
        return String.format("$%.2f", result);
    }



    private String getDailyAverageStr()
    {
        if (expenseList.isEmpty()) {
            return "$0";
        }

        Map<String, Double> dailyExpenses = new HashMap<>();
        for (Expense expense : expenseList){
            dailyExpenses.put(expense.getDateStr(), dailyExpenses.getOrDefault(expense.getDateStr(), 0.0) + expense.getCost());
        }

        double total = 0.0;
        for (double dailyTotal : dailyExpenses.values()){
            total += dailyTotal;
        }

        int uniqueDays = dailyExpenses.size();
        double dailyAverage = total / uniqueDays / 100;
        return String.format("$%.2f", dailyAverage);
    }



    private String getMonthlyAverageStr()
    {
        if (expenseList.isEmpty()) {
            return "$0";
        }

        Map<String, Double> monthlyExpenses = new HashMap<>();
        for (Expense expense : expenseList){
            monthlyExpenses.put(expense.getDate().hashByMonth(), monthlyExpenses.getOrDefault(expense.getDate().hashByMonth(), 0.0) + expense.getCost());
        }

        double total = 0.0;
        for (double monthlyTotal : monthlyExpenses.values()){
            total += monthlyTotal;
        }

        int uniqueMonths = monthlyExpenses.size();
        double monthlyAverage = total / uniqueMonths / 100;
        return String.format("$%.2f", monthlyAverage);
    }
}