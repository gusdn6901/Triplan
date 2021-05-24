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

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.daoimpl.ProfileDAOImpl;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.daoimpl.TripDAOImpl;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ProfileDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public class AddScheduleActivity extends AppCompatActivity {
    EditText addSchedule_dateText, addSchedule_timeText, addSchedule_scheduleText;
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
        addSchedule_scheduleText = findViewById(R.id.addSchedule_scheduleText);

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

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        addSchedule_dateText.setText(sdf.format(scheduleCalendar.getTime()));
    }

    public void clickAddScheduleCompleteBtn(View view) {
        if(addSchedule_dateText.getText().toString().equals("")) {
            Toast.makeText(this, "날짜를 입력하세요", Toast.LENGTH_SHORT).show();
            addSchedule_dateText.requestFocus();
        } else if(addSchedule_timeText.getText().toString().equals("")) {
            Toast.makeText(this, "시간을 입력하세요", Toast.LENGTH_SHORT).show();
            addSchedule_timeText.requestFocus();
        } else if(addSchedule_scheduleText.getText().toString().equals("")) {
            Toast.makeText(this, "일정을 입력하세요", Toast.LENGTH_SHORT).show();
            addSchedule_scheduleText.requestFocus();
        } else {
            ScheduleDTO schedule = new ScheduleDTO();
            schedule.setSchedule_name(addSchedule_scheduleText.getText().toString());
            schedule.setSchedule_trip_id(getIntent().getIntExtra("tripId", -1));
            schedule.setSchedule_date_year(Integer.parseInt(addSchedule_dateText.getText().toString().split("/")[0]));
            schedule.setSchedule_date_month(Integer.parseInt(addSchedule_dateText.getText().toString().split("/")[1]));
            schedule.setSchedule_date_day(Integer.parseInt(addSchedule_dateText.getText().toString().split("/")[2]));
            schedule.setSchedule_hour(Integer.parseInt(addSchedule_timeText.getText().toString().split(":")[0]));
            schedule.setSchedule_minute(Integer.parseInt(addSchedule_timeText.getText().toString().split(":")[1]));

            ScheduleDAO scheduleDAO = new ScheduleDAO(this);
            scheduleDAO.insertSchedule(schedule);

            setResult(RESULT_OK);
            finish();
        }
    }
    public void clickAddScheduleCancelBtn(View view) { finish(); }
}

