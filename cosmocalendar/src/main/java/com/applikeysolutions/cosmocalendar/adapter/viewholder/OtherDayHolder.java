package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.graphics.Color;
import android.view.View;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.BaseSelectionManager;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.applikeysolutions.cosmocalendar.view.customviews.CircleAnimationTextView;
import com.applikeysolutions.customizablecalendar.R;

public class OtherDayHolder extends BaseDayHolder {

    public OtherDayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        ctvDay = (CircleAnimationTextView) itemView.findViewById(R.id.tv_day_number);
        ctvDay.setText(null);
    }

    public void bind(Day firstDayInMonth, final Day day, BaseSelectionManager selectionManager) {
        this.selectionManager = selectionManager;

        boolean isSelected = selectionManager.isDaySelected(day);
        if (isSelected && selectionManager.isMonthInRange(firstDayInMonth)) {
            ctvDay.setBackgroundColor(calendarView.getSelectedDayBackgroundColor());
        } else {
            ctvDay.setBackgroundColor(Color.TRANSPARENT);
        }
    }


}
