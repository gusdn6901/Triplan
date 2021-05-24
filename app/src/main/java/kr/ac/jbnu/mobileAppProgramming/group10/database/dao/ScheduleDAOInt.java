package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public interface ScheduleDAOInt {
    ScheduleDTO getSchedule(int schedule_id);

    boolean insertTrip(ScheduleDTO scheduleDTO);

    boolean updateTrip(ScheduleDTO scheduleDTO);

    boolean deleteTrip(int id);

    int numberOfSchedule();

    List<ScheduleDTO> getScedulesOfTrip(int trip_id);
}
