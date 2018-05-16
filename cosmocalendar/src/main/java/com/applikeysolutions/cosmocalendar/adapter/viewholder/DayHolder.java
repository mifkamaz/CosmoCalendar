package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.BaseSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.SelectionState;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.applikeysolutions.cosmocalendar.view.customviews.CircleAnimationTextView;
import com.applikeysolutions.customizablecalendar.R;

public class DayHolder extends BaseDayHolder {

    public DayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        ctvDay = (CircleAnimationTextView) itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day, BaseSelectionManager selectionManager) {
        this.selectionManager = selectionManager;
        ctvDay.setText(String.valueOf(day.getDayNumber()));

        boolean isSelected = selectionManager.isDaySelected(day);
        if (isSelected) {
            ctvDay.setBackgroundColor(calendarView.getSelectedDayBackgroundColor());
            SelectionState state;
            if (selectionManager instanceof RangeSelectionManager) {
                state = ((RangeSelectionManager) selectionManager).getSelectedState(day);
            } else {
                state = SelectionState.SINGLE_DAY;
            }
            select2(state);
        } else {
            select2(null);
            if (day.isDisabled()) {
                ctvDay.setTextColor(calendarView.getDisabledDayTextColor());
            }
        }

        if (day.isCurrent()) {
            addCurrentDayIcon(isSelected);
        }
    }


    private void select2(SelectionState state) {
        if (state == null) {
            ctvDay.setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        switch (state) {
            case START_RANGE_DAY_WITHOUT_END:
                break;
            case START_RANGE_DAY:
                ctvDay.setBackgroundDrawable(drawRectangle());
                break;
            case END_RANGE_DAY:
                ctvDay.setBackgroundDrawable(drawRectangle2());
                break;
            case RANGE_DAY:
                ctvDay.setBackgroundColor(calendarView.getSelectedDayBackgroundColor());
                break;
            case SINGLE_DAY:
//                ctvDay.setBackgroundColor(calendarView.getSelectedDayBackgroundStartColor());
                break;
        }

    }

    private Drawable drawRectangle() {

        ShapeDrawable round = new ShapeDrawable();
        round.setShape(new OvalShape());
        round.getPaint().setColor(calendarView.getSelectedDayBackgroundStartColor());

        PaintDrawable rectangle = new PaintDrawable();
        rectangle.setShape(
                new RoundRectShape(
                        new float[]{1000f,1000f, 0, 0, 0, 0, 1000f,1000f},
                        null,
                        null
                )
        );
        rectangle.getPaint().setColor(calendarView.getSelectedDayBackgroundColor());

        return new LayerDrawable(new Drawable[]{
                rectangle, round
        });
    }

    private Drawable drawRectangle2() {

        PaintDrawable rectangle = new PaintDrawable();
        rectangle.setShape(
                new RoundRectShape(
                        new float[]{ 0, 0,1000f,1000f, 1000f,1000f, 0, 0},
                        null,
                        null
                )
        );
        rectangle.getPaint().setColor(calendarView.getSelectedDayBackgroundColor());

        return rectangle;
    }

}
