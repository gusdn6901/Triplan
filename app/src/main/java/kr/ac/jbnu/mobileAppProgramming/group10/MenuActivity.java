package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void clickAddTripBtn(View view) {
        Intent intent = new Intent(MenuActivity.this, AddTripActivity.class);
        startActivity(intent);
    }
    public void clickTripListBtn(View view) {
        Intent intent = new Intent(MenuActivity.this, TripListActivitiy.class);
        startActivity(intent);
    }
}
