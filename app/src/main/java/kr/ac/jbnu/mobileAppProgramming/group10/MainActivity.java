package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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
//                String username = sharedPref.getString("username", null);
//                String password = sharedPref.getString("password", null);
//                if (username == null && password == null) {
//                    Intent intent = new Intent(MainActivity.this, Log_in.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(MainActivity.this);
//                    String profile_email = sharedPreferencesManager.getEmail();
//                    Intent intent = new Intent(MainActivity.this, NavigatonDrawer.class);
//                    intent.putExtra("profileEmail", profile_email);
//                    startActivity(intent);
//                    finish();
//                }
                int tripId = sharedPref.getInt("tripId", -1);
                if(tripId != -1) {
                    ScheduleDAO scheduleDAO = new ScheduleDAO(MainActivity.this);
                    List<ScheduleDTO> schedules = scheduleDAO.getSchedulesOfTrip(tripId);
                    Iterator<ScheduleDTO> iter = schedules.iterator();
//                    while(iter.hasNext()) {
//
//                    }

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
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
