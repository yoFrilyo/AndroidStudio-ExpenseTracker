package com.example.assignment2;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.io.Serializable;

public class Expense implements Parcelable, Serializable {
    private String label;
    private Date date;
    private int cost;
    private String reason;
    private String notes;



    public Expense(EditText LABEL, Date DATE, EditText COST, EditText REASON, EditText NOTES)
    {
        date = DATE;
        label = LABEL.getText().toString();
        reason = REASON.getText().toString();
        notes = NOTES.getText().toString();
        cost = getCents(COST);
    }

    protected Expense(Parcel in) {
        label = in.readString();
        date = in.readParcelable(Date.class.getClassLoader());
        cost = in.readInt();
        reason = in.readString();
        notes = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel source) {
            return new Expense(source);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(label);
        dest.writeParcelable(date, flags);
        dest.writeInt(cost);
        dest.writeString(reason);
        dest.writeString(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getLabel() {return label;}
    public int getCost() {return cost;}
    public String getCostString()
    {
        float result = cost / 100;
        return String.format("$%.2f", result);
    }

    public String getCostStringWithoutDollarSign()
    {
        float result = cost / 100;
        return String.format("%.2f", result);
    }

    public Date getDate() {return date;}

    public String getDateStr() {return date.getString();}

    public String getReason() {return reason;}

    public String getNotes() {return notes;}



    private int getCents(EditText COST){
        int cents;

        String input = COST.getText().toString().trim();
        float dollarAmount = Float.parseFloat(input);
        cents = (int) (dollarAmount * 100);

        return cents;
    }
}
