package kr.ac.jbnu.mobileAppProgramming.group10.database;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public interface DBServiceInt {
    TripDTO getTrip(int trip_id);
    boolean updateTrip(TripDTO tripDTO);
    boolean insertTrip(TripDTO tripDTO);
    boolean deleteTrip(int trip_id);
    List<TripDTO> getAllTrips();
    TripDTO getCurrentTrip();
    void resetCurrentTrip();

    boolean insertSchedule(ScheduleDTO scheduleDTO);
    boolean updateSchedule(ScheduleDTO scheduleDTO);
    boolean deleteSchedule(int id);
    boolean deleteScheduleByTrip(int trip_id);
    List<ScheduleDTO> getSchedulesForDate(int trip_id, int year, int month, int day);
}
