package kr.ac.jbnu.mobileAppProgramming.group10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripListActivitiy extends AppCompatActivity {
    RecyclerView tripList_recyclerView;
    TripAdapter tripAdapter;
    List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripList_recyclerView = findViewById(R.id.tripList_recyclerView);

        strings.add("00");
        tripList_recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        tripList_recyclerView.setLayoutManager(lm1);
        tripAdapter = new TripAdapter(TripListActivitiy.this, strings);
        tripList_recyclerView.setAdapter(tripAdapter);
    }
}
