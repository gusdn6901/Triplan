package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBContract;
import kr.ac.jbnu.mobileAppProgramming.group10.database.DBHelper;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.TripDTO;

public class TripDAO implements TripDAOInt {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public TripDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    @Override
    public TripDTO getTrip(int trip_id) {
        TripDTO tripDTO = new TripDTO();

        String condition = DBContract.COLUMN_NAME_TRIP_ID + "= ?";
        String[] param = new String[]{String.valueOf(trip_id)};
        Cursor cursor = database.query(DBContract.TABLE_NAME_TRIP,
                null, condition, param, null, null, null);

        while (cursor.moveToNext()) {
            tripDTO.setTrip_id(cursor.getInt(0));
            tripDTO.setTrip_name(cursor.getString(1));
            tripDTO.setTrip_location(cursor.getString(2));
            tripDTO.setTrip_start_date_year(cursor.getInt(3));
            tripDTO.setTrip_start_date_month(cursor.getInt(4));
            tripDTO.setTrip_start_date_day(cursor.getInt(5));
            tripDTO.setTrip_end_date_year(cursor.getInt(6));
            tripDTO.setTrip_end_date_month(cursor.getInt(7));
            tripDTO.setTrip_end_date_day(cursor.getInt(8));
            tripDTO.setTrip_is_current(cursor.getInt(9));
        }

        cursor.close();
        return tripDTO;
    }

    @Override
    public TripDTO getTripByName(String trip_name) {
        TripDTO tripDTO = new TripDTO();

        String condition = DBContract.COLUMN_NAME_TRIP_NAME + "= ?";
        String[] param = new String[]{String.valueOf(trip_name)};
        Cursor cursor = database.query(DBContract.TABLE_NAME_TRIP,
                null, condition, param, null, null, null);

        while (cursor.moveToNext()) {
            tripDTO.setTrip_id(cursor.getInt(0));
            tripDTO.setTrip_name(cursor.getString(1));
            tripDTO.setTrip_location(cursor.getString(2));
            tripDTO.setTrip_start_date_year(cursor.getInt(3));
            tripDTO.setTrip_start_date_month(cursor.getInt(4));
            tripDTO.setTrip_start_date_day(cursor.getInt(5));
            tripDTO.setTrip_end_date_year(cursor.getInt(6));
            tripDTO.setTrip_end_date_month(cursor.getInt(7));
            tripDTO.setTrip_end_date_day(cursor.getInt(8));
            tripDTO.setTrip_is_current(cursor.getInt(9));
        }

        cursor.close();
        return tripDTO;
    }

    @Override
    public boolean insertTrip(TripDTO tripDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.COLUMN_NAME_TRIP_NAME, tripDTO.getTrip_name());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_LOCATION, tripDTO.getTrip_location());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_YEAR, tripDTO.getTrip_start_date_year());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_MONTH, tripDTO.getTrip_start_date_month());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_DAY, tripDTO.getTrip_start_date_day());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_YEAR, tripDTO.getTrip_end_date_year());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_MONTH, tripDTO.getTrip_end_date_month());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_DAY, tripDTO.getTrip_end_date_day());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_IS_CURRENT, tripDTO.getTrip_is_current());
        long insert_trip = database.insert(DBContract.TABLE_NAME_TRIP, null, contentValues);

        return insert_trip == 1;
    }

    @Override
    public boolean updateTrip(TripDTO tripDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.COLUMN_NAME_TRIP_ID, tripDTO.getTrip_id());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_NAME, tripDTO.getTrip_name());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_LOCATION, tripDTO.getTrip_location());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_YEAR, tripDTO.getTrip_start_date_year());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_MONTH, tripDTO.getTrip_start_date_month());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_START_DATE_DAY, tripDTO.getTrip_start_date_day());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_YEAR, tripDTO.getTrip_end_date_year());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_MONTH, tripDTO.getTrip_end_date_month());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_END_DATE_DAY, tripDTO.getTrip_end_date_day());
        contentValues.put(DBContract.COLUMN_NAME_TRIP_IS_CURRENT, tripDTO.getTrip_is_current());

        String condition = DBContract.COLUMN_NAME_TRIP_ID + "= ?";
        String[] param = new String[]{String.valueOf(tripDTO.getTrip_id())};
        int update_trip = database.update(DBContract.TABLE_NAME_TRIP, contentValues, condition, param);

        return update_trip == 1;
    }

    @Override
    public boolean deleteTrip(int id) {
        String condition = DBContract.COLUMN_NAME_TRIP_ID + "= ?";
        String[] param = new String[]{String.valueOf(id)};
        int delete_trip = database.delete(DBContract.TABLE_NAME_TRIP, condition, param);

        return delete_trip == 1;
    }

    @Override
    public int numberOfTrip() {
        int count = 0;
        Cursor cursor = database.query(DBContract.TABLE_NAME_TRIP,null, null, null, null, null, null);
        while (cursor.moveToNext()) count++;
        cursor.close();

        return count;
    }

    @Override
    public void resetCurrentTrip() {
        List<TripDTO> trips = getAllTrips();
        Iterator<TripDTO> iter = trips.iterator();
        while(iter.hasNext()) {
            TripDTO trip = iter.next();
            trip.setTrip_is_current(0);
            updateTrip(trip);
        }
    }

    @Override
    public TripDTO getCurrentTrip() {
        TripDTO tripDTO = new TripDTO();
        String condition = DBContract.COLUMN_NAME_TRIP_IS_CURRENT + "= 1";
        Cursor cursor = database.query(DBContract.TABLE_NAME_TRIP,
                null, condition, null, null, null, null);
        while (cursor.moveToNext()) {
            tripDTO.setTrip_id(cursor.getInt(0));
            tripDTO.setTrip_name(cursor.getString(1));
            tripDTO.setTrip_location(cursor.getString(2));
            tripDTO.setTrip_start_date_year(cursor.getInt(3));
            tripDTO.setTrip_start_date_month(cursor.getInt(4));
            tripDTO.setTrip_start_date_day(cursor.getInt(5));
            tripDTO.setTrip_end_date_year(cursor.getInt(6));
            tripDTO.setTrip_end_date_month(cursor.getInt(7));
            tripDTO.setTrip_end_date_day(cursor.getInt(8));
            tripDTO.setTrip_is_current(cursor.getInt(9));
        }

        cursor.close();
        return tripDTO;
    }

    @Override
    public List<TripDTO> getAllTrips() {
        List<TripDTO> trips = new ArrayList<>();

        Cursor cursor = database.query(DBContract.TABLE_NAME_TRIP,null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            TripDTO tripDTO = new TripDTO();
            tripDTO.setTrip_id(cursor.getInt(0));
            tripDTO.setTrip_name(cursor.getString(1));
            tripDTO.setTrip_location(cursor.getString(2));
            tripDTO.setTrip_start_date_year(cursor.getInt(3));
            tripDTO.setTrip_start_date_month(cursor.getInt(4));
            tripDTO.setTrip_start_date_day(cursor.getInt(5));
            tripDTO.setTrip_end_date_year(cursor.getInt(6));
            tripDTO.setTrip_end_date_month(cursor.getInt(7));
            tripDTO.setTrip_end_date_day(cursor.getInt(8));
            tripDTO.setTrip_is_current(cursor.getInt(9));
            trips.add(tripDTO);
        }

        cursor.close();
        return trips;
    }
}
