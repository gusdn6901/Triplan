package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    TextView menu_text_noneCurrent;
    TextView menu_text_current_prev, menu_text_current_current, menu_text_current_next;
    LinearLayout menu_current_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_text_noneCurrent = findViewById(R.id.menu_text_noneCurrent);
        menu_current_layout = findViewById(R.id.menu_current_layout);
        menu_text_current_prev = findViewById(R.id.menu_text_current_prev);
        menu_text_current_current = findViewById(R.id.menu_text_current_current);
        menu_text_current_next = findViewById(R.id.menu_text_current_next);

        int tripId = getIntent().getIntExtra("tripId", -1);
        String prevSchedule = getIntent().getStringExtra("prevSchedule");
        String currentSchedule = getIntent().getStringExtra("currentSchedule");
        String nextSchedule = getIntent().getStringExtra("nextSchedule");
        if(tripId == -1) {
            menu_text_noneCurrent.setVisibility(View.VISIBLE);
            menu_current_layout.setVisibility(View.GONE);
        } else {
            menu_text_current_prev.setText(prevSchedule);
            menu_text_current_current.setText(currentSchedule);
            menu_text_current_next.setText(nextSchedule);
        }
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
