package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBService;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class ScheduleListActivity extends AppCompatActivity {
    TextView scheduleList_yearText, scheduleList_monthText, scheduleList_dayText;
    RecyclerView scheduleList_scheduleRecyclerView;
    ImageButton scheduleList_prevBtn, scheduleList_nextBtn;
    List<ScheduleDTO> schedules;
    int year, month, day;
    int tripId;

    final public static int SCHEDULE_ADDED_REQUEST = 1;
    final public static int SCHEDULE_MODIFIED_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        tripId = getIntent().getIntExtra("tripId", -1);
        scheduleList_yearText = findViewById(R.id.scheduleList_yearText);
        scheduleList_monthText = findViewById(R.id.scheduleList_monthText);
        scheduleList_dayText = findViewById(R.id.scheduleList_dayText);
        scheduleList_scheduleRecyclerView = findViewById(R.id.scheduleList_scheduleRecyclerView);
        scheduleList_prevBtn = findViewById(R.id.scheduleList_prevBtn);
        scheduleList_nextBtn = findViewById(R.id.scheduleList_nextBtn);

        TripDTO tripDTO = DBService.getInstance(this).getTrip(tripId);

        year = tripDTO.getTrip_start_date_year().intValue();
        month = tripDTO.getTrip_start_date_month().intValue();
        day = tripDTO.getTrip_start_date_day().intValue();

        drawView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ScheduleDTO schedule = new ScheduleDTO();
        schedule.setSchedule_name(data.getStringExtra("name"));
        schedule.setSchedule_trip_id(getIntent().getIntExtra("tripId", -1));
        schedule.setSchedule_date_year(data.getIntExtra("year", 1900));
        schedule.setSchedule_date_month(data.getIntExtra("month", 1));
        schedule.setSchedule_date_day(data.getIntExtra("day", 1));
        schedule.setSchedule_hour(Integer.parseInt(data.getStringExtra("time").split(":")[0]));
        schedule.setSchedule_minute(Integer.parseInt(data.getStringExtra("time").split(":")[1]));
        if(requestCode == SCHEDULE_ADDED_REQUEST && resultCode == RESULT_OK) {
            DBService.getInstance(this).insertSchedule(schedule);

            Toast.makeText(this, "일정 추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            drawView();
        } else if(requestCode == SCHEDULE_MODIFIED_REQUEST && resultCode == RESULT_OK) {
            schedule.setSchedule_id(data.getIntExtra("id", -1));
            DBService.getInstance(this).updateSchedule(schedule);

            Toast.makeText(this, "일정 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            drawView();
        }
    }

    public void drawView() {
        schedules = new ArrayList<>();

        TripDTO tripDTO = DBService.getInstance(this).getTrip(tripId);
        if(tripDTO.getTrip_start_date_year() == year && tripDTO.getTrip_start_date_month() == month && tripDTO.getTrip_start_date_day() == day) scheduleList_prevBtn.setClickable(false);
        else scheduleList_prevBtn.setClickable(true);
        if(tripDTO.getTrip_end_date_year() == year && tripDTO.getTrip_end_date_month() == month && tripDTO.getTrip_end_date_day() == day) scheduleList_nextBtn.setClickable(false);
        else scheduleList_nextBtn.setClickable(true);

        scheduleList_yearText.setText(String.valueOf(year));
        scheduleList_monthText.setText(String.valueOf(month));
        scheduleList_dayText.setText(String.valueOf(day));

        schedules = DBService.getInstance(this).getSchedulesForDate(tripId, year, month, day);

        Collections.sort(schedules);

        scheduleList_scheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        scheduleList_scheduleRecyclerView.setLayoutManager(layoutManager);
        ScheduleAdapter schedulePrevAdapter = new ScheduleAdapter(ScheduleListActivity.this, schedules);
        scheduleList_scheduleRecyclerView.setAdapter(schedulePrevAdapter);
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
        Intent intent = new Intent(ScheduleListActivity.this, ManageScheduleActivity.class);
        intent.putExtra("tripId", tripId);
        startActivityForResult(intent, SCHEDULE_ADDED_REQUEST);
    }
}
