package kr.ac.jbnu.mobileAppProgramming.group10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBService;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.TripDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class TripListActivity extends AppCompatActivity {
    RecyclerView tripList_recyclerView;
    TripAdapter tripAdapter;
    List<TripDTO> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripList_recyclerView = findViewById(R.id.tripList_recyclerView);

        trips = DBService.getInstance(this).getAllTrips();

        tripAdapter = new TripAdapter(TripListActivity.this, trips);
        tripList_recyclerView.setAdapter(tripAdapter);

        tripList_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tripList_recyclerView.setLayoutManager(layoutManager);
    }
}
