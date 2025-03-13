package com.example.assignment2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{

    private List<Expense> expenseList;
    private OnExpenseClickListener listener;



    public interface OnExpenseClickListener {
        //void onExpenseClick(Expense expense);
        void onExpenseClick(int position);
    }



    public void setOnItemClickListener(OnExpenseClickListener listener)
    {
        this.listener = listener;
    }



    public ExpenseAdapter(List<Expense> expenseList)
    {
        this.expenseList = expenseList;
    }



    @Override @NonNull
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.expense_layout, parent, false);
        ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(listItem, listener);
        return expenseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.label.setText(expense.getLabel());
        holder.date.setText(expense.getDateStr());
        holder.cost.setText(expense.getCostString());
        Log.d("RecyclerView", "Binding item at position: " + position);
        //holder.itemView.setOnClickListener(v -> listener.onExpenseClick(expense));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public TextView date;
        public TextView cost;

        public ExpenseViewHolder(@NonNull View itemView, final OnExpenseClickListener listener) {
            super(itemView);
            label = itemView.findViewById(R.id.label_id);
            date = itemView.findViewById(R.id.date_id);
            cost = itemView.findViewById(R.id.cost_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onExpenseClick(position);
                        }
                    }
                }
            });
        }
    }
}
