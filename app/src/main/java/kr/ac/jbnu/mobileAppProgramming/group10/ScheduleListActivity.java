package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListActivity extends AppCompatActivity {
    RecyclerView scheduleList_prevScheduleRecyclerView, scheduleList_currentScheduleRecyclerView, scheduleList_nextScheduleRecyclerView;
    SchedulePrevAdapter schedulePrevAdapter;
    ScheduleCurrentAdapter scheduleCurrentAdapter;
    ScheduleNextAdapter scheduleNextAdapter;
    List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        scheduleList_prevScheduleRecyclerView = findViewById(R.id.scheduleList_prevScheduleRecyclerView);
        scheduleList_currentScheduleRecyclerView = findViewById(R.id.scheduleList_currentScheduleRecyclerView);
        scheduleList_nextScheduleRecyclerView = findViewById(R.id.scheduleList_nextScheduleRecyclerView);

        strings.add("00");
        scheduleList_prevScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        scheduleList_prevScheduleRecyclerView.setLayoutManager(lm1);
        schedulePrevAdapter = new SchedulePrevAdapter(ScheduleListActivity.this, strings);
        scheduleList_prevScheduleRecyclerView.setAdapter(schedulePrevAdapter);

        scheduleList_currentScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        scheduleList_currentScheduleRecyclerView.setLayoutManager(lm2);
        scheduleCurrentAdapter = new ScheduleCurrentAdapter(ScheduleListActivity.this, strings);
        scheduleList_currentScheduleRecyclerView.setAdapter(scheduleCurrentAdapter);

        scheduleList_nextScheduleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager lm3 = new LinearLayoutManager(this);
        scheduleList_nextScheduleRecyclerView.setLayoutManager(lm3);
        scheduleNextAdapter = new ScheduleNextAdapter(ScheduleListActivity.this, strings);
        scheduleList_nextScheduleRecyclerView.setAdapter(scheduleNextAdapter);
    }

    public void clickAddScheduleBtn(View view) {
        Intent intent = new Intent(ScheduleListActivity.this, AddScheduleActivity.class);
        startActivity(intent);
    }
}
