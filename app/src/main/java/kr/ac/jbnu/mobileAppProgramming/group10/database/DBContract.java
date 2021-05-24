package kr.ac.jbnu.mobileAppProgramming.group10.database;

public class DBContract {
    //TABLE_NAME
    public static final String TABLE_NAME_TRIP = "trip";
    public static final String TABLE_NAME_SCHEDULE = "schedule";

    //TRIP_COLUMN_NAME
    public static final String COLUMN_NAME_TRIP_ID = "trip_id";
    public static final String COLUMN_NAME_TRIP_NAME = "trip_name";
    public static final String COLUMN_NAME_TRIP_LOCATION = "trip_location";
    public static final String COLUMN_NAME_TRIP_START_DATE_YEAR = "trip_start_date_year";
    public static final String COLUMN_NAME_TRIP_START_DATE_MONTH = "trip_start_date_month";
    public static final String COLUMN_NAME_TRIP_START_DATE_DAY = "trip_start_date_day";
    public static final String COLUMN_NAME_TRIP_END_DATE_YEAR = "trip_end_date_year";
    public static final String COLUMN_NAME_TRIP_END_DATE_MONTH = "trip_end_date_month";
    public static final String COLUMN_NAME_TRIP_END_DATE_DAY = "trip_end_date_day";
    public static final String COLUMN_NAME_TRIP_IS_CURRENT = "trip_is_current";

    //SCHEDULE_COLUMN_NAME
    public static final String COLUMN_NAME_SCHEDULE_ID = "schedule_id";
    public static final String COLUMN_NAME_SCHEDULE_TRIP_ID = "schedule_trip_id";
    public static final String COLUMN_NAME_SCHEDULE_NAME = "schedule_name";
    public static final String COLUMN_NAME_SCHEDULE_DATE_YEAR = "schedule_date_year";
    public static final String COLUMN_NAME_SCHEDULE_DATE_MONTH = "schedule_date_month";
    public static final String COLUMN_NAME_SCHEDULE_DATE_DAY = "schedule_date_day";
    public static final String COLUMN_NAME_SCHEDULE_HOUR = "schedule_hour";
    public static final String COLUMN_NAME_SCHEDULE_MINUTE = "schedule_minute";

    //CREATE_TRIP_TABLE
    public static final String SQL_CREATE_TABLE_TRIP =
            "CREATE TABLE " + TABLE_NAME_TRIP + "(" +
                    COLUMN_NAME_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_TRIP_NAME + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_NAME_TRIP_LOCATION + " TEXT NOT NULL, " +
                    COLUMN_NAME_TRIP_START_DATE_YEAR + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_START_DATE_MONTH + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_START_DATE_DAY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_END_DATE_YEAR + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_END_DATE_MONTH + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_END_DATE_DAY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_TRIP_IS_CURRENT + " INTEGER DEFAULT 0, " +");";

    //CREATE_SCHEDULE_TABLE
    public static final String SQL_CREATE_TABLE_SCHEDULE =
            "CREATE TABLE " + TABLE_NAME_SCHEDULE + "(" +
                    COLUMN_NAME_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_SCHEDULE_TRIP_ID + " INTEGER UNIQUE NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_NAME + " TEXT NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_DATE_YEAR + " INTEGER NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_DATE_MONTH + " INTEGER NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_DATE_DAY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_HOUR + " INTEGER NOT NULL, " +
                    COLUMN_NAME_SCHEDULE_MINUTE + " INTEGER NOT NULL, " +");";

    //DROP_TRIP_TABLE
    public static final String SQL_DROP_TABLE_TRIP =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TRIP;
    //DROP_SCHEDULE_TABLE
    public static final String SQL_DROP_TABLE_SCHEDULE =
            "DROP TABLE IF EXISTS " + TABLE_NAME_SCHEDULE;
}
