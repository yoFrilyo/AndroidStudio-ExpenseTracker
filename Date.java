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
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "MONTH";
        }
    }

    public String getString()
    {
        return getMonthString() + " " + day + " " + year;
    }
}
