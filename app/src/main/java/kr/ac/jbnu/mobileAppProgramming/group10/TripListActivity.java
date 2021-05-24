package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.TripDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.daoimpl.TripDAOImpl;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.TripDTO;

public class TripListActivity extends AppCompatActivity {
    RecyclerView tripList_recyclerView;
    TripAdapter tripAdapter;
    List<TripDTO> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripList_recyclerView = findViewById(R.id.tripList_recyclerView);

        TripDAO tripDAO = new TripDAO(TripListActivity.this);
        trips = tripDAO.getAllTrips();

        tripAdapter = new TripAdapter(TripListActivity.this, trips);
        tripList_recyclerView.setAdapter(tripAdapter);

        tripList_recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tripList_recyclerView.setLayoutManager(layoutManager);
    }
}
