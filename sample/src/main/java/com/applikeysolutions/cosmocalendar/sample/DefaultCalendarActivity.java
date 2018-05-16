package com.applikeysolutions.cosmocalendar.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultCalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;

    private boolean fridayCriteriaEnabled;
    private boolean threeMonthsCriteriaEnabled;

    private MenuItem menuFridays;
    private MenuItem menuThreeMonth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_calendar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        initViews();
        createCriterias();
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);

        calendarView.setSelectedDayBackgroundStartColor(Color.RED);
        calendarView.setSelectionBarMonthTextColor(Color.RED);

        final Calendar calendar = Calendar.getInstance();
        Set<Long> disabledDays = new ArraySet<>();

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        disabledDays.add(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        disabledDays.add(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        disabledDays.add(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        disabledDays.add(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        disabledDays.add(calendar.getTimeInMillis());

//        calendarView.setDisabledDays(disabledDays);

        calendarView.setWeekendDays(new HashSet() {{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});

//        calendar.add(Calendar.DAY_OF_YEAR, 3);
//        calendarView.toggleDay(calendar);
//        calendar.add(Calendar.DAY_OF_YEAR, 3);
//        calendarView.toggleDay(calendar);

        calendarView.setCurrentDayIconRes(R.drawable.bg_empty);
        calendarView.setCurrentDaySelectedIconRes(R.drawable.bg_empty);

        calendarView.setShowDaysOfWeek(false);
        calendarView.setShowDaysOfWeekTitle(false);


//        calendarView.setOnDaySelectedListener(
//                new CalendarView.OnDaySelectedListener() {
//                    @Override
//                    public void onDaySelected(List<Day> selectedDays) {
//                        Calendar calendar1 = Calendar.getInstance();
//                        calendar1.setTime(selectedDays.get(0).getCalendar().getTime());
//
//                        calendar1.add(Calendar.DAY_OF_YEAR, 7);
//
//                        calendarView.clearSelections();
//                        calendarView.toggleDay(selectedDays.get(0).getCalendar());
//                        calendarView.toggleDay(calendar1);
//                    }
//                }
//        );

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            int days = 4;
//
//            @Override
//            public void run() {
//                final Calendar start = Calendar.getInstance();
//                final Calendar end = Calendar.getInstance();
//                end.add(Calendar.DAY_OF_YEAR, days);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        calendarView.clearSelections();
//                        calendarView.toggleDay(start);
//                        calendarView.toggleDay(end);
//                    }
//                });
//
//                days++;
//
//                if (days > 20) {
//                    days = 4;
//                }
//            }
//        }, 1000, 250);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setMax(16);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                final Calendar start = Calendar.getInstance();
                final Calendar end = Calendar.getInstance();

                end.add(Calendar.DAY_OF_YEAR, i + 4);

                calendarView.clearSelections();
                calendarView.toggleDay(start);
                calendarView.toggleDay(end);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);

        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default_calendar_activity, menu);
        menuFridays = menu.findItem(R.id.select_all_fridays);
        menuThreeMonth = menu.findItem(R.id.select_three_months);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all_fridays:
                fridayMenuClick();
                return true;

            case R.id.select_three_months:
                threeMonthsMenuClick();
                return true;

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.log_selected_days:
                logSelectedDaysMenuClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.setTitle(getString(R.string.select_all_fridays));
            unselectAllFridays();
        } else {
            menuFridays.setTitle(getString(R.string.unselect_all_fridays));
            selectAllFridays();
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled;
    }

    private void threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.setTitle(getString(R.string.select_three_months));
            unselectThreeMonths();
        } else {
            menuThreeMonth.setTitle(getString(R.string.unselect_three_months));
            selectThreeMonths();
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled;
    }

    private void selectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void unselectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void selectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void unselectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

        fridayCriteriaEnabled = false;
        threeMonthsCriteriaEnabled = false;
        menuFridays.setTitle(getString(R.string.select_all_fridays));
        menuThreeMonth.setTitle(getString(R.string.select_three_months));
    }


    private void logSelectedDaysMenuClick() {
        Toast.makeText(this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
    }
}

