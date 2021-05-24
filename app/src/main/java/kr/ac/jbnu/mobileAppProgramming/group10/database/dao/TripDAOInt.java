package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.TripDTO;

public interface TripDAOInt {
    TripDTO getTrip(int trip_id);

    TripDTO getTripByName(String trip_name);

    boolean insertTrip(TripDTO tripDTO);

    boolean updateTrip(TripDTO tripDTO);

    boolean deleteTrip(int id);

    int numberOfTrip();

    TripDTO getCurrentTrip();
    List<TripDTO> getAllTrips();
}
