package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public interface ScheduleDAOInt {
    ScheduleDTO getSchedule(int schedule_id);

    boolean insertSchedule(ScheduleDTO scheduleDTO);

    boolean updateSchedule(ScheduleDTO scheduleDTO);

    boolean deleteSchedule(int id);

    boolean deleteScheduleByTrip(int trip_id);

    int numberOfSchedule();

    List<ScheduleDTO> getSchedulesForDate(int trip_id, int year, int month, int day);

    List<ScheduleDTO> getSchedulesOfTrip(int trip_id);
}
