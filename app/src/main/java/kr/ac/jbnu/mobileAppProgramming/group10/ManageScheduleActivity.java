package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBService;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class ManageScheduleActivity extends AppCompatActivity {
    EditText manageSchedule_dateText, manageSchedule_timeText, manageSchedule_scheduleText;
    public int hours;
    public int minutes;
    int years, months, days;
    Calendar scheduleCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);
        manageSchedule_dateText = findViewById(R.id.manageSchedule_dateText);
        manageSchedule_timeText = findViewById(R.id.manageSchedule_timeText);
        manageSchedule_scheduleText = findViewById(R.id.manageSchedule_scheduleText);

        if(getIntent().getBooleanExtra("isModify", false)) {
            int yearText = getIntent().getIntExtra("year", 0);
            int monthText = getIntent().getIntExtra("month", 0);
            int dayText = getIntent().getIntExtra("day", 0);
            int hourText = getIntent().getIntExtra("hour", 0);
            int minuteText = getIntent().getIntExtra("minute", -1);
            String scheduleText = getIntent().getStringExtra("name");

            manageSchedule_dateText.setText(yearText + "/" + monthText + "/" + dayText);
            manageSchedule_timeText.setText(hourText + ":" + minuteText);
            manageSchedule_scheduleText.setText(scheduleText);
        }

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

                updateDateLabel();
            }
        };

        manageSchedule_dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ManageScheduleActivity.this, date, scheduleCalendar
                        .get(Calendar.YEAR), scheduleCalendar.get(Calendar.MONTH),
                        scheduleCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        manageSchedule_timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hourString = String.valueOf(selectedHour);
                        String minuteString = String.valueOf(selectedMinute);
                        if(selectedHour < 10) hourString = "0" + String.valueOf(selectedHour);
                        if(selectedMinute < 10) minuteString = "0"+ String.valueOf(selectedMinute);
                        manageSchedule_timeText.setText(hourString + ":" + minuteString);
                        minutes = selectedMinute;
                        hours = selectedHour;
                        scheduleCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        scheduleCalendar.set(Calendar.MINUTE, selectedMinute - 1);
                        scheduleCalendar.set(Calendar.SECOND, 59);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        manageSchedule_dateText.setText(sdf.format(scheduleCalendar.getTime()));
    }

    public void clickManageScheduleCompleteBtn(View view) {
        TripDTO tripDTO =  DBService.getInstance(this).getTrip(getIntent().getIntExtra("tripId", -1));
        int yearText = manageSchedule_dateText.getText().toString().equals("") ? 0 : Integer.parseInt(manageSchedule_dateText.getText().toString().split("/")[0]);
        int monthText = manageSchedule_dateText.getText().toString().equals("") ? 0 : Integer.parseInt(manageSchedule_dateText.getText().toString().split("/")[1]);
        int dayText = manageSchedule_dateText.getText().toString().equals("") ? 0 : Integer.parseInt(manageSchedule_dateText.getText().toString().split("/")[2]);
        if(manageSchedule_dateText.getText().toString().equals("")) {
            Toast.makeText(this, "날짜를 입력하세요", Toast.LENGTH_SHORT).show();
            manageSchedule_dateText.requestFocus();
        } else if(manageSchedule_timeText.getText().toString().equals("")) {
            Toast.makeText(this, "시간을 입력하세요", Toast.LENGTH_SHORT).show();
            manageSchedule_timeText.requestFocus();
        } else if(manageSchedule_scheduleText.getText().toString().equals("")) {
            Toast.makeText(this, "일정을 입력하세요", Toast.LENGTH_SHORT).show();
            manageSchedule_scheduleText.requestFocus();
        } else if(tripDTO.getTrip_start_date_year() > yearText || tripDTO.getTrip_end_date_year() < yearText) {
            Toast.makeText(this, "날짜가 여행 일시에서 벗어났습니다.", Toast.LENGTH_SHORT).show();
            manageSchedule_dateText.requestFocus();
        } else if(tripDTO.getTrip_start_date_month() > monthText || tripDTO.getTrip_end_date_month() < monthText) {
            Toast.makeText(this, "날짜가 여행 일시에서 벗어났습니다.", Toast.LENGTH_SHORT).show();
            manageSchedule_dateText.requestFocus();
        } else if(tripDTO.getTrip_start_date_day() > dayText || tripDTO.getTrip_end_date_day() < dayText) {
            Toast.makeText(this, "날짜가 여행 일시에서 벗어났습니다.", Toast.LENGTH_SHORT).show();
            manageSchedule_dateText.requestFocus();
        } else {
            Intent data = new Intent();
            data.putExtra("id", getIntent().getIntExtra("scheduleId", -1));
            data.putExtra("name", manageSchedule_scheduleText.getText().toString());
            data.putExtra("year", yearText);
            data.putExtra("month", monthText);
            data.putExtra("day", dayText);
            data.putExtra("time", manageSchedule_timeText.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        }
    }
    public void clickManageScheduleCancelBtn(View view) { finish(); }
}

