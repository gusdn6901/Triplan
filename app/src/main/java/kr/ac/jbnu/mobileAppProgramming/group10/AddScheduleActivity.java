package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.daoimpl.ProfileDAOImpl;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.daoimpl.TripDAOImpl;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ProfileDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class AddScheduleActivity extends AppCompatActivity {
    EditText addSchedule_dateText, addSchedule_timeText, addSchedule_locationText;
    public int hours = 2;
    public int minutes;
    int years, months, days;
    Calendar scheduleCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        addSchedule_dateText = findViewById(R.id.addSchedule_dateText);
        addSchedule_timeText = findViewById(R.id.addSchedule_timeText);
        addSchedule_locationText = findViewById(R.id.addSchedule_locationText);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                years = year;
                months = monthOfYear;
                days = dayOfMonth;
                scheduleCalendar.set(Calendar.YEAR, year);
                scheduleCalendar.set(Calendar.MONTH, monthOfYear);
                scheduleCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

        // when alarmDate editText is clicked
        addSchedule_dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddScheduleActivity.this, date, scheduleCalendar
                        .get(Calendar.YEAR), scheduleCalendar.get(Calendar.MONTH),
                        scheduleCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // click listener on alarmClock EditText
        addSchedule_timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addSchedule_timeText.setText(selectedHour + ":" + selectedMinute);
                        minutes = selectedMinute;
                        hours = selectedHour;
                        scheduleCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        scheduleCalendar.set(Calendar.MINUTE, selectedMinute - 1);
                        scheduleCalendar.set(Calendar.SECOND, 59);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    // update alarmDate Text
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        addSchedule_dateText.setText(sdf.format(scheduleCalendar.getTime()));
    }

    public void clickAddScheduleCompleteBtn(View view) {
        finish();
    }
    public void clickAddScheduleCancelBtn(View view) { finish(); }
}

