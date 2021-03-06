package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBService;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class AddTripActivity extends AppCompatActivity {
    EditText add_trip_nameText, add_trip_locationText, add_trip_startDateText, add_trip_endDateText;
    int years, months, days;
    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        add_trip_nameText = findViewById(R.id.add_trip_nameText);
        add_trip_locationText = findViewById(R.id.add_trip_locationText);
        add_trip_startDateText = findViewById(R.id.add_trip_startDateText);
        add_trip_endDateText = findViewById(R.id.add_trip_endDateText);

        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                years = year;
                months = monthOfYear;
                days = dayOfMonth;
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }
        };

        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                years = year;
                months = monthOfYear;
                days = dayOfMonth;
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDate();
            }
        };

        add_trip_startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTripActivity.this, startDate, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        add_trip_endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTripActivity.this, endDate, endCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartDate() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        add_trip_startDateText.setText(sdf.format(startCalendar.getTime()));
    }
    private void updateEndDate() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        add_trip_endDateText.setText(sdf.format(endCalendar.getTime()));
    }

    public void clickAddTripCompleteBtn(View view) {
        String tripName = add_trip_nameText.getText().toString();
        String tripLocation = add_trip_locationText.getText().toString();
        String startDate = add_trip_startDateText.getText().toString();
        String endDate = add_trip_endDateText.getText().toString();

        int startYear = (startDate.equals("")) ? 0 : Integer.parseInt(startDate.split("/")[0]);
        int startMonth = (startDate.equals("")) ? 0 : Integer.parseInt(startDate.split("/")[1]);
        int startDay = (startDate.equals("")) ? 0 : Integer.parseInt(startDate.split("/")[2]);
        int endYear = (endDate.equals("")) ? 0 : Integer.parseInt(endDate.split("/")[0]);
        int endMonth = (endDate.equals("")) ? 0 : Integer.parseInt(endDate.split("/")[1]);
        int endDay = (endDate.equals("")) ? 0 : Integer.parseInt(endDate.split("/")[2]);

        if(startDate.equals("")) {
            Toast.makeText(this, "?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
            add_trip_startDateText.requestFocus();
        } else if(endDate.equals("")) {
            Toast.makeText(this, "?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
            add_trip_endDateText.requestFocus();
        } else if(tripName.equals("")) {
            Toast.makeText(this, "?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
            add_trip_nameText.requestFocus();
        } else if(tripLocation.equals("")) {
            Toast.makeText(this, "?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
            add_trip_locationText.requestFocus();
        }  else if(startYear > endYear) {
            Toast.makeText(this, "???????????? ??????????????? ????????????.",Toast.LENGTH_SHORT).show();
        } else if(startYear == endYear && startMonth > endMonth) {
            Toast.makeText(this, "???????????? ??????????????? ????????????.",Toast.LENGTH_SHORT).show();
        } else if(startYear == endYear && startMonth == endMonth && startDay > endDay) {
            Toast.makeText(this, "???????????? ??????????????? ????????????.",Toast.LENGTH_SHORT).show();
        } else {
            String[] startDates = startDate.split("/");
            String[] endDates = endDate.split("/");

            TripDTO trip = new TripDTO();
            trip.setTrip_name(tripName);
            trip.setTrip_location(tripLocation);
            trip.setTrip_start_date_year(Integer.parseInt(startDates[0]));
            trip.setTrip_start_date_month(Integer.parseInt(startDates[1]));
            trip.setTrip_start_date_day(Integer.parseInt(startDates[2]));
            trip.setTrip_end_date_year(Integer.parseInt(endDates[0]));
            trip.setTrip_end_date_month(Integer.parseInt(endDates[1]));
            trip.setTrip_end_date_day(Integer.parseInt(endDates[2]));
            trip.setTrip_is_current(0);

            DBService.getInstance(this).insertTrip(trip);
            finish();
        }
    }
    public void clickCancelBtn(View view) { finish(); }
}
