package kr.ac.jbnu.mobileAppProgramming.group10.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Iterator;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.TripDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class DBService implements DBServiceInt {
    private static TripDAO tripDAO;
    private static ScheduleDAO scheduleDAO;
    private static DBHelper dbHelper;
    static SQLiteDatabase database;

    private DBService() {}
    private static DBService instance = new DBService();
    public static DBService getInstance(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
        tripDAO = TripDAO.getInstance(dbHelper);
        scheduleDAO = ScheduleDAO.getInstance(dbHelper);
        return instance;
    }
    public static DBService getInstance() { return instance; }

    public TripDTO getTrip(int trip_id) { return tripDAO.getTrip(trip_id); }
    public boolean updateTrip(TripDTO tripDTO) { return tripDAO.updateTrip(tripDTO); }
    public boolean insertTrip(TripDTO tripDTO) { return tripDAO.insertTrip(tripDTO); }
    public boolean deleteTrip(int trip_id) { return tripDAO.deleteTrip(trip_id); }
    public List<TripDTO> getAllTrips() { return tripDAO.getAllTrips(); }
    public TripDTO getCurrentTrip() { return tripDAO.getCurrentTrip(); }
    public void resetCurrentTrip() {
        List<TripDTO> trips = tripDAO.getAllTrips();
        Iterator<TripDTO> iter = trips.iterator();
        while(iter.hasNext()) {
            TripDTO trip = iter.next();
            trip.setTrip_is_current(0);
            tripDAO.updateTrip(trip);
        }
    }

    public boolean insertSchedule(ScheduleDTO scheduleDTO) { return scheduleDAO.insertSchedule(scheduleDTO); }
    public boolean updateSchedule(ScheduleDTO scheduleDTO) { return scheduleDAO.updateSchedule(scheduleDTO); }
    public boolean deleteSchedule(int id) { return scheduleDAO.deleteSchedule(id); }
    public boolean deleteScheduleByTrip(int trip_id) { return scheduleDAO.deleteScheduleByTrip(trip_id); }
    public List<ScheduleDTO> getSchedulesForDate(int trip_id, int year, int month, int day) { return scheduleDAO.getSchedulesForDate(trip_id, year, month, day); }
}
