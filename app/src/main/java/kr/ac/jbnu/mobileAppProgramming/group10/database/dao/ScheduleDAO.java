package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBContract;
import kr.ac.jbnu.mobileAppProgramming.group10.database.DBHelper;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;

public class ScheduleDAO implements ScheduleDAOInt {
    static SQLiteDatabase database;

    private static ScheduleDAO instance = new ScheduleDAO();
    private ScheduleDAO() {}
    public static ScheduleDAO getInstance(DBHelper dbHelper) { database = dbHelper.getReadableDatabase(); return instance; }

    @Override
    public ScheduleDTO getSchedule(int schedule_id) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        String condition = DBContract.COLUMN_NAME_SCHEDULE_ID + "= ?";
        String[] param = new String[]{String.valueOf(schedule_id)};
        Cursor cursor = database.query(DBContract.TABLE_NAME_SCHEDULE,
                null, condition, param, null, null, null);

        while (cursor.moveToNext()) {
            scheduleDTO.setSchedule_id(cursor.getInt(0));
            scheduleDTO.setSchedule_trip_id(cursor.getInt(1));
            scheduleDTO.setSchedule_name(cursor.getString(2));
            scheduleDTO.setSchedule_date_year(cursor.getInt(3));
            scheduleDTO.setSchedule_date_month(cursor.getInt(4));
            scheduleDTO.setSchedule_date_day(cursor.getInt(5));
            scheduleDTO.setSchedule_hour(cursor.getInt(6));
            scheduleDTO.setSchedule_minute(cursor.getInt(7));
        }
        cursor.close();
        return scheduleDTO;
    }

    @Override
    public boolean insertSchedule(ScheduleDTO scheduleDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_TRIP_ID, scheduleDTO.getSchedule_trip_id());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_NAME, scheduleDTO.getSchedule_name());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_YEAR, scheduleDTO.getSchedule_date_year());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_MONTH, scheduleDTO.getSchedule_date_month());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_DAY, scheduleDTO.getSchedule_date_day());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_HOUR, scheduleDTO.getSchedule_hour());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_MINUTE, scheduleDTO.getSchedule_minute());
        long insert_schedule = database.insert(DBContract.TABLE_NAME_SCHEDULE, null, contentValues);

        return insert_schedule == 1;
    }

    @Override
    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_TRIP_ID, scheduleDTO.getSchedule_trip_id());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_NAME, scheduleDTO.getSchedule_name());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_YEAR, scheduleDTO.getSchedule_date_year());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_MONTH, scheduleDTO.getSchedule_date_month());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_DATE_DAY, scheduleDTO.getSchedule_date_day());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_HOUR, scheduleDTO.getSchedule_hour());
        contentValues.put(DBContract.COLUMN_NAME_SCHEDULE_MINUTE, scheduleDTO.getSchedule_minute());

        String condition = DBContract.COLUMN_NAME_SCHEDULE_ID + "= ?";
        String[] param = new String[]{String.valueOf(scheduleDTO.getSchedule_id())};
        int update_schedule = database.update(DBContract.TABLE_NAME_SCHEDULE, contentValues, condition, param);

        return update_schedule == 1;
    }

    @Override
    public boolean deleteSchedule(int id) {
        String condition = DBContract.COLUMN_NAME_SCHEDULE_ID + "= ?";
        String[] param = new String[]{String.valueOf(id)};
        int delete_schedule = database.delete(DBContract.TABLE_NAME_SCHEDULE, condition, param);

        return delete_schedule == 1;
    }

    @Override
    public boolean deleteScheduleByTrip(int trip_id) {
        String condition = DBContract.COLUMN_NAME_SCHEDULE_TRIP_ID + "= ?";
        String[] param = new String[]{String.valueOf(trip_id)};
        int delete_schedule = database.delete(DBContract.TABLE_NAME_SCHEDULE, condition, param);

        return delete_schedule == 1;
    }

    @Override
    public int numberOfSchedule() {
        int count = 0;
        Cursor cursor = database.query(DBContract.TABLE_NAME_SCHEDULE,null, null, null, null, null, null);
        while (cursor.moveToNext()) count++;
        cursor.close();

        return count;
    }

    @Override
    public List<ScheduleDTO> getSchedulesForDate(int trip_id, int year, int month, int day) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        String condition = DBContract.COLUMN_NAME_SCHEDULE_TRIP_ID + "= ? AND "
                + DBContract.COLUMN_NAME_SCHEDULE_DATE_YEAR + "= ? AND "
                + DBContract.COLUMN_NAME_SCHEDULE_DATE_MONTH + "= ? AND "
                + DBContract.COLUMN_NAME_SCHEDULE_DATE_DAY + "= ?";
        String[] param = new String[]{String.valueOf(trip_id), String.valueOf(year), String.valueOf(month), String.valueOf(day)};
        Cursor cursor = database.query(DBContract.TABLE_NAME_SCHEDULE,
                null, condition, param, null, null, null);
        while (cursor.moveToNext()) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setSchedule_id(cursor.getInt(0));
            scheduleDTO.setSchedule_trip_id(cursor.getInt(1));
            scheduleDTO.setSchedule_name(cursor.getString(2));
            scheduleDTO.setSchedule_date_year(cursor.getInt(3));
            scheduleDTO.setSchedule_date_month(cursor.getInt(4));
            scheduleDTO.setSchedule_date_day(cursor.getInt(5));
            scheduleDTO.setSchedule_hour(cursor.getInt(6));
            scheduleDTO.setSchedule_minute(cursor.getInt(7));
            schedules.add(scheduleDTO);
        }

        cursor.close();
        return schedules;
    }

    @Override
    public List<ScheduleDTO> getSchedulesOfTrip(int trip_id) {
        List<ScheduleDTO> schedules = new ArrayList<>();

        String condition = DBContract.COLUMN_NAME_SCHEDULE_TRIP_ID + "= ?";
        String[] param = new String[]{String.valueOf(trip_id)};
        Cursor cursor = database.query(DBContract.TABLE_NAME_SCHEDULE,
                null, condition, param, null, null, null);

        while(cursor.moveToNext()) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setSchedule_id(cursor.getInt(0));
            scheduleDTO.setSchedule_trip_id(cursor.getInt(1));
            scheduleDTO.setSchedule_name(cursor.getString(2));
            scheduleDTO.setSchedule_date_year(cursor.getInt(3));
            scheduleDTO.setSchedule_date_month(cursor.getInt(4));
            scheduleDTO.setSchedule_date_day(cursor.getInt(5));
            scheduleDTO.setSchedule_hour(cursor.getInt(6));
            scheduleDTO.setSchedule_minute(cursor.getInt(7));
            schedules.add(scheduleDTO);
        }

        cursor.close();
        return schedules;
    }

    public List<ScheduleDTO> getSchedules() {
        List<ScheduleDTO> schedules = new ArrayList<>();
        Cursor cursor = database.query(DBContract.TABLE_NAME_SCHEDULE,
                null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setSchedule_id(cursor.getInt(0));
            scheduleDTO.setSchedule_trip_id(cursor.getInt(1));
            scheduleDTO.setSchedule_name(cursor.getString(2));
            scheduleDTO.setSchedule_date_year(cursor.getInt(3));
            scheduleDTO.setSchedule_date_month(cursor.getInt(4));
            scheduleDTO.setSchedule_date_day(cursor.getInt(5));
            scheduleDTO.setSchedule_hour(cursor.getInt(6));
            scheduleDTO.setSchedule_minute(cursor.getInt(7));
            schedules.add(scheduleDTO);
        }

        cursor.close();
        return schedules;
    }
}
