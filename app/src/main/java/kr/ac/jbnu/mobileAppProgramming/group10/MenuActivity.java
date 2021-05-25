package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public class MenuActivity extends AppCompatActivity {
    TextView menu_text_noneCurrent;
    TextView menu_text_current_prev, menu_text_current_current, menu_text_current_next;
    Button menu_currentTripBtn;
    LinearLayout menu_current_layout;

    String prevSchedule;
    String currentSchedule;
    String nextSchedule;
    int tripId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_text_noneCurrent = findViewById(R.id.menu_text_noneCurrent);
        menu_current_layout = findViewById(R.id.menu_current_layout);
        menu_text_current_prev = findViewById(R.id.menu_text_current_prev);
        menu_text_current_current = findViewById(R.id.menu_text_current_current);
        menu_text_current_next = findViewById(R.id.menu_text_current_next);
        menu_currentTripBtn = findViewById(R.id.menu_currentTripBtn);

        tripId = getIntent().getIntExtra("tripId", -1);
        prevSchedule = getIntent().getStringExtra("prevSchedule");
        currentSchedule = getIntent().getStringExtra("currentSchedule");
        nextSchedule = getIntent().getStringExtra("nextSchedule");
        drawView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPref = getSharedPreferences("currentTrip", Context.MODE_PRIVATE);
        tripId = sharedPref.getInt("tripId", -1);
        if(tripId != -1) {
            ScheduleDAO scheduleDAO = new ScheduleDAO(this);
            List<ScheduleDTO> prevSchedules = new ArrayList<>();
            List<ScheduleDTO> currentSchedules = new ArrayList<>();
            List<ScheduleDTO> nextSchedules = new ArrayList<>();
            Date now = new Date(System.currentTimeMillis());
            String getTime = new SimpleDateFormat("yyyy/MM/dd/HH/mm").format(now);
            int nowYear = Integer.parseInt(getTime.split("/")[0]);
            int nowMonth = Integer.parseInt(getTime.split("/")[1]);
            int nowDay = Integer.parseInt(getTime.split("/")[2]);
            int nowHour = Integer.parseInt(getTime.split("/")[3]);
            int nowMinute = Integer.parseInt(getTime.split("/")[4]);

            List<ScheduleDTO> schedules = scheduleDAO.getSchedulesForDate(tripId, nowYear, nowMonth, nowDay);
            Collections.sort(schedules);

            Iterator<ScheduleDTO> iter = schedules.iterator();
            while(iter.hasNext()) {
                ScheduleDTO schedule = iter.next();
                if(schedule.getSchedule_date_year() < nowYear)
                    prevSchedules.add(schedule);
                else if(schedule.getSchedule_date_month() < nowMonth)
                    prevSchedules.add(schedule);
                else if(schedule.getSchedule_date_day() < nowDay)
                    prevSchedules.add(schedule);
                else if(schedule.getSchedule_date_year() > nowYear)
                    nextSchedules.add(schedule);
                else if(schedule.getSchedule_date_month() > nowMonth)
                    nextSchedules.add(schedule);
                else if(schedule.getSchedule_date_day() > nowDay)
                    nextSchedules.add(schedule);
                else if(schedule.getSchedule_hour() < nowHour)
                    prevSchedules.add(schedule);
                else if(schedule.getSchedule_hour() > nowHour)
                    nextSchedules.add(schedule);
                else if(schedule.getSchedule_minute() < nowMinute)
                    prevSchedules.add(schedule);
                else if(schedule.getSchedule_minute() > nowMinute)
                    nextSchedules.add(schedule);
                else
                    currentSchedules.add(schedule);
            }

            if(!currentSchedule.isEmpty()) {
                if(!prevSchedules.isEmpty()) prevSchedule = prevSchedules.get(prevSchedules.size()-1).getSchedule_name();
                if(currentSchedules.size() > 1) {
                    currentSchedule = currentSchedules.get(0).getSchedule_name();
                   nextSchedule =  currentSchedules.get(1).getSchedule_name();
                } else {
                    currentSchedule = currentSchedules.get(0).getSchedule_name();
                    if(!nextSchedules.isEmpty()) nextSchedule = nextSchedules.get(0).getSchedule_name();
                }
            } else {
                if(!prevSchedules.isEmpty()) {
                    if(prevSchedules.size() > 1) prevSchedule = prevSchedules.get(prevSchedules.size()-2).getSchedule_name();
                    currentSchedule = prevSchedules.get(prevSchedules.size()-1).getSchedule_name();
                    if(!nextSchedules.isEmpty()) nextSchedule = nextSchedules.get(0).getSchedule_name();
                } else {
                    if(!nextSchedules.isEmpty()) nextSchedule = nextSchedules.get(0).getSchedule_name();
                }
            }
        }
        drawView();
    }

    private void drawView() {
        if(tripId == -1) {
            menu_text_noneCurrent.setVisibility(View.VISIBLE);
            menu_current_layout.setVisibility(View.GONE);
        } else {
            menu_text_noneCurrent.setVisibility(View.GONE);
            menu_current_layout.setVisibility(View.VISIBLE);

            menu_text_current_prev.setText(prevSchedule);
            menu_text_current_current.setText(currentSchedule);
            menu_text_current_next.setText(nextSchedule);
            menu_currentTripBtn.setClickable(true);
        }
    }

    public void clickCurrentTripBtn(View view) {
        Intent intent = new Intent(MenuActivity.this, ScheduleListActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

    public void clickAddTripBtn(View view) {
        Intent intent = new Intent(MenuActivity.this, AddTripActivity.class);
        startActivity(intent);
    }
    public void clickTripListBtn(View view) {
        Intent intent = new Intent(MenuActivity.this, TripListActivity.class);
        startActivity(intent);
    }
}
