package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.TripDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.TripDTO;

public class MainActivity extends AppCompatActivity {

    private static int splash_Time_Out = 3000;
    SharedPreferences sharedPref;
    ImageView splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashScreen = findViewById(R.id.splashScreen);
        sharedPref = getSharedPreferences("currentTrip", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int tripId = sharedPref.getInt("tripId", -1);
                if(tripId != -1) {
                    ScheduleDAO scheduleDAO = new ScheduleDAO(MainActivity.this);
                    List<ScheduleDTO> prevSchedule = new ArrayList<>();
                    List<ScheduleDTO> currentSchedule = new ArrayList<>();
                    List<ScheduleDTO> nextSchedule = new ArrayList<>();
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
                            prevSchedule.add(schedule);
                        else if(schedule.getSchedule_date_month() < nowMonth)
                            prevSchedule.add(schedule);
                        else if(schedule.getSchedule_date_day() < nowDay)
                            prevSchedule.add(schedule);
                        else if(schedule.getSchedule_date_year() > nowYear)
                            nextSchedule.add(schedule);
                        else if(schedule.getSchedule_date_month() > nowMonth)
                            nextSchedule.add(schedule);
                        else if(schedule.getSchedule_date_day() > nowDay)
                            nextSchedule.add(schedule);
                        else if(schedule.getSchedule_hour() < nowHour)
                            prevSchedule.add(schedule);
                        else if(schedule.getSchedule_hour() > nowHour)
                            nextSchedule.add(schedule);
                        else if(schedule.getSchedule_minute() < nowMinute)
                            prevSchedule.add(schedule);
                        else if(schedule.getSchedule_minute() > nowMinute)
                            nextSchedule.add(schedule);
                        else
                            currentSchedule.add(schedule);
                    }

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    if(!currentSchedule.isEmpty()) {
                        if(!prevSchedule.isEmpty()) intent.putExtra("prevSchedule", prevSchedule.get(prevSchedule.size()-1).getSchedule_name());
                        if(currentSchedule.size() > 1) {
                            intent.putExtra("currentSchedule", currentSchedule.get(0).getSchedule_name());
                            intent.putExtra("nextSchedule", currentSchedule.get(1).getSchedule_name());
                        } else {
                            intent.putExtra("currentSchedule", currentSchedule.get(0).getSchedule_name());
                            if(!nextSchedule.isEmpty()) intent.putExtra("nextSchedule", nextSchedule.get(0).getSchedule_name());
                        }
                    } else {
                        if(!prevSchedule.isEmpty()) {
                            if(prevSchedule.size() > 1) intent.putExtra("prevSchedule", prevSchedule.get(prevSchedule.size()-2).getSchedule_name());
                            intent.putExtra("currentSchedule", prevSchedule.get(prevSchedule.size()-1).getSchedule_name());
                            if(!nextSchedule.isEmpty()) intent.putExtra("nextSchedule", nextSchedule.get(0).getSchedule_name());
                        } else {
                            if(!nextSchedule.isEmpty()) intent.putExtra("nextSchedule", nextSchedule.get(0).getSchedule_name());
                        }
                    }

                    intent.putExtra("tripId", tripId);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, splash_Time_Out);
    }

}
