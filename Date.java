package com.example.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

public class Date implements Parcelable {
    public int day;
    public int month;
    public int year;



    public Date(int DAY, int MONTH, int YEAR)
    {
        day = DAY;
        month = MONTH;
        year = YEAR;
    }

    protected Date(Parcel in){
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
    }

    public static final Creator<Date> CREATOR = new Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel source) {
            return new Date(source);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
    }

    @Override
    public int describeContents(){
        return 0;
    }



    public String getMonthString()
    {
        switch (month) {
            case 0:
                return "JAN";
            case 1:
                return "FEB";
            case 2:
                return "MAR";
            case 3:
                return "APR";
            case 4:
                return "MAY";
            case 5:
                return "JUN";
            case 6:
                return "JUL";
            case 7:
                return "AUG";
            case 8:
                return "SEP";
            case 9:
                return "OCT";
            case 10:
                return "NOV";
            case 11:
                return "DEC";
            default:
                return "MONTH";
        }
    }



    public String getString()
    {
        return getMonthString() + "/" + day + "/" + year;
    }



    public String hashByMonth()
    {
        return getMonthString() + "/" + year;
    }



    public boolean sameDay(Date date)
    {
        return this.day ==  date.day && this.month == date.month && this.year == date.year;
    }



    public boolean sameMonth(Date date)
    {
        return this.month == date.month && this.year == date.year;
    }



    public boolean sameYear(Date date)
    {
        return this.year == date.year;
    }
}
