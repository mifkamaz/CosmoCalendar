package com.applikeysolutions.cosmocalendar.view.customviews;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.SelectionState;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

public class StateDrawer {

    private SelectionState selectionState;
    private CalendarView calendarView;

    private boolean clearView;
    private boolean stateChanged;

    //Circle
    private Paint circlePaint;
    private Paint circleUnderPaint;
    private int circleColor;

    //Start/End range half rectangle
    private Paint rectanglePaint;
    private Rect rectangle;

    //Rectangle
    private Paint backgroundRectanglePaint;
    private Rect backgroundRectangle;

    public static final int DEFAULT_PADDING = 0;
    public static final int MAX_PROGRESS = 100;
    public static final long SELECTION_ANIMATION_DURATION = 300;

    public void draw(Canvas canvas) {
        if (selectionState != null) {
            switch (selectionState) {
                case START_RANGE_DAY:
                case END_RANGE_DAY:
                    drawRectangle(canvas);
                    drawCircleUnder(canvas);
                    drawCircle(canvas);
                    break;

                case START_RANGE_DAY_WITHOUT_END:
                    drawCircle(canvas);
                    break;

                case SINGLE_DAY:
                    drawCircle(canvas);
                    break;

                case RANGE_DAY:
                    drawBackgroundRectangle(canvas);
                    break;
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        if (circlePaint == null || stateChanged) {
            createCirclePaint();
        }

        final int diameter = getWidth() - DEFAULT_PADDING * 2;

        canvas.drawCircle(getWidth() / 2, getWidth() / 2, diameter / 2, circlePaint);
    }

    private void drawCircleUnder(Canvas canvas) {
        if (circleUnderPaint == null || stateChanged) {
            createCircleUnderPaint();
        }
        final int diameter = getWidth() - DEFAULT_PADDING * 2;
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, diameter / 2, circleUnderPaint);
    }

    private void createCirclePaint() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void createCircleUnderPaint() {
        circleUnderPaint = new Paint();
        circleUnderPaint.setColor(calendarView.getSelectedDayBackgroundColor());
        circleUnderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawRectangle(Canvas canvas) {
        if (rectanglePaint == null) {
            createRectanglePaint();
        }
        if (rectangle == null) {
            rectangle = getRectangleForState();
        }
        canvas.drawRect(rectangle, rectanglePaint);
    }

    private void createRectanglePaint() {
        rectanglePaint = new Paint();
        rectanglePaint.setColor(calendarView.getSelectedDayBackgroundColor());
        rectanglePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawBackgroundRectangle(Canvas canvas) {
        if (backgroundRectanglePaint == null) {
            createBackgroundRectanglePaint();
        }
        if (backgroundRectangle == null) {
            backgroundRectangle = getRectangleForState();
        }
        canvas.drawRect(backgroundRectangle, backgroundRectanglePaint);
    }

    private void createBackgroundRectanglePaint() {
        backgroundRectanglePaint = new Paint();
        backgroundRectanglePaint.setColor(calendarView.getSelectedDayBackgroundColor());
        backgroundRectanglePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private Rect getRectangleForState() {
        switch (selectionState) {
            case START_RANGE_DAY:
                return new Rect(getWidth() / 2, DEFAULT_PADDING, getWidth(), getHeight() - DEFAULT_PADDING);

            case END_RANGE_DAY:
                return new Rect(0, DEFAULT_PADDING, getWidth() / 2, getHeight() - DEFAULT_PADDING);

            case RANGE_DAY:
                return new Rect(0, DEFAULT_PADDING, getWidth(), getHeight() - DEFAULT_PADDING);

            default:
                return null;
        }
    }

    int getWidth() {
        return 1;
    }

    int getHeight() {
        return 1;
    }

}
