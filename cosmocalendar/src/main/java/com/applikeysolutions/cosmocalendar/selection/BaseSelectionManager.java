package com.applikeysolutions.cosmocalendar.selection;

import android.support.annotation.NonNull;

import com.applikeysolutions.cosmocalendar.model.Day;

public abstract class BaseSelectionManager {

    protected OnDaySelectedListener onDaySelectedListener;

    public abstract void toggleDay(@NonNull Day day, boolean notify);

    public abstract boolean isDaySelected(@NonNull Day day);

    public boolean isMonthInRange(Day firstMonthDay) {
        return false;
    }


    public abstract void clearSelections();

    public BaseSelectionManager() {
    }
}
