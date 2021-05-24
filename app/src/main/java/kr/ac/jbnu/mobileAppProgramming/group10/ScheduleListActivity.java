package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ScheduleListActivity extends AppCompatActivity {
    TextView scheduleList_yearText, scheduleList_monthText, scheduleList_dayText;
    RecyclerView scheduleList_prevScheduleRecyclerView, scheduleList_currentScheduleRecyclerView, scheduleList_nextScheduleRecyclerView;
    Button scheduleList_prevBtn, scheduleList_nextBtn;
    List<ScheduleDTO> schedules;
    List<ScheduleDTO> prevSchedules;
    List<ScheduleDTO> currentSchedules;
    List<ScheduleDTO> nextSchedules;
    int year, month, day;
    int tripId;

    final static int SCHEDULE_ADDED_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        tripId = getIntent().getIntExtra("tripId", -1);

        scheduleList_yearText = findViewById(R.id.scheduleList_yearText);
        scheduleList_monthText = findViewById(R.id.scheduleList_monthText);
        scheduleList_dayText = findViewById(R.id.scheduleList_dayText);
        scheduleList_prevScheduleRecyclerView = findViewById(R.id.scheduleList_prevScheduleRecyclerView);
        scheduleList_currentScheduleRecyclerView = findViewById(R.id.scheduleList_currentScheduleRecyclerView);
        scheduleList_nextScheduleRecyclerView = findViewById(R.id.scheduleList_nextScheduleRecyclerView);
        scheduleList_prevBtn = findViewById(R.id.scheduleList_prevBtn);
        scheduleList_nextBtn = findViewById(R.id.scheduleList_nextBtn);

        TripDAO tripDAO = new TripDAO(this);
        TripDTO tripDTO = tripDAO.getTrip(tripId);

        year = tripDTO.getTrip_start_date_year().intValue();
        month = tripDTO.getTrip_start_date_month().intValue();
        day = tripDTO.getTrip_start_date_day().intValue();

        drawView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SCHEDULE_ADDED_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "일정 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            prevSchedules = new ArrayList<>();
            currentSchedules = new ArrayList<>();
            nextSchedules = new ArrayList<>();
            drawView();
        }
    }

    public void drawView() {
        schedules = new ArrayList<>();
        prevSchedules = new ArrayList<>();
        currentSchedules = new ArrayList<>();
        nextSchedules = new ArrayList<>();

        TripDAO tripDAO = new TripDAO(this);
        TripDTO tripDTO = tripDAO.getTrip(tripId);
        if(tripDTO.getTrip_start_date_year() == year && tripDTO.getTrip_start_date_month() == month && tripDTO.getTrip_start_date_day() == day) scheduleList_prevBtn.setClickable(false);
        else scheduleList_prevBtn.setClickable(true);
        if(tripDTO.getTrip_end_date_year() == year && tripDTO.getTrip_end_date_month() == month && tripDTO.getTrip_end_date_day() == day) scheduleList_nextBtn.setClickable(false);
        else scheduleList_nextBtn.setClickable(true);

        scheduleList_yearText.setText(String.valueOf(year));
        scheduleList_monthText.setText(String.valueOf(month));
        scheduleList_dayText.setText(String.valueOf(day));

        ScheduleDAO scheduleDAO = new ScheduleDAO(this);
        schedules = scheduleDAO.getSchedulesForDate(tripId, year, month, day);

        Collections.sort(schedules);

        Date now = new Date(System.currentTimeMillis());
        String getTime = new SimpleDateFormat("yyyy/MM/dd/HH/mm").format(now);
        int nowYear = Integer.parseInt(getTime.split("/")[0]);
        int nowMonth = Integer.parseInt(getTime.split("/")[1]);
        int nowDay = Integer.parseInt(getTime.split("/")[2]);
        int nowHour = Integer.parseInt(getTime.split("/")[3]);
        int nowMinute = Integer.parseInt(getTime.split("/")[4]);
        System.out.println(getTime);

        Iterator<ScheduleDTO> iter = schedules.iterator();
        while(iter.hasNext()) {
            ScheduleDTO schedule = iter.next();

            //날짜 검사
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
            //시간 검사
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

        scheduleList_prevScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        scheduleList_prevScheduleRecyclerView.setLayoutManager(lm1);
        SchedulePrevAdapter schedulePrevAdapter = new SchedulePrevAdapter(ScheduleListActivity.this, prevSchedules);
        scheduleList_prevScheduleRecyclerView.setAdapter(schedulePrevAdapter);

        scheduleList_currentScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        scheduleList_currentScheduleRecyclerView.setLayoutManager(lm2);
        ScheduleCurrentAdapter scheduleCurrentAdapter = new ScheduleCurrentAdapter(ScheduleListActivity.this, currentSchedules);
        scheduleList_currentScheduleRecyclerView.setAdapter(scheduleCurrentAdapter);

        scheduleList_nextScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm3 = new LinearLayoutManager(this);
        scheduleList_nextScheduleRecyclerView.setLayoutManager(lm3);
        ScheduleNextAdapter scheduleNextAdapter = new ScheduleNextAdapter(ScheduleListActivity.this, nextSchedules);
        scheduleList_nextScheduleRecyclerView.setAdapter(scheduleNextAdapter);
    }

    public void clickScheduleListPrevBtn(View view) {
        day--;
        if(day <= 0 && year % 100 != 0 && year % 4 == 0 && month == 3) {
            month--;
            day = 29;
        } else if(day <= 0 && month == 3) {
            month--;
            day = 28;
        } else if((month == 2 || month == 4 || month == 6 || month == 9 || month == 11) && day <= 0) {
            month--;
            day = 31;
        } else if(month == 1 && day <= 0) {
            year--;
            month = 12;
            day = 31;
        } else if((month == 5 || month == 7 || month == 8 || month == 10 || month==12) && day <= 0) {
            month--;
            day = 31;
        }
        drawView();
    }

    public void clickScheduleListNextBtn(View view) {
        day++;
        if(year % 4 == 0 && year % 100 != 0 && month == 2 && day>=30) {
            month++;
            day = 1;
        } else if(month == 2 && day >= 29) {
            month++;
            day = 1;
        } else if((month == 4 || month == 6 || month == 9 || month == 11) && day >= 31) {
            month++;
            day = 1;
        } else if(month == 12 && day >= 32) {
            year++;
            month = 1;
            day = 1;
        } else if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) && day >= 32) {
            month++;
            day = 1;
        }
        drawView();
    }

    public void clickAddScheduleBtn(View view) {
        Intent intent = new Intent(ScheduleListActivity.this, AddScheduleActivity.class);
        intent.putExtra("tripId", tripId);
        startActivityForResult(intent, SCHEDULE_ADDED_REQUEST);
    }
}
