package kr.ac.jbnu.mobileAppProgramming.group10.database.dao;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public interface TripDAOInt {
    TripDTO getTrip(int trip_id);

    boolean insertTrip(TripDTO tripDTO);

    boolean updateTrip(TripDTO tripDTO);

    boolean deleteTrip(int id);

    int numberOfTrip();

    TripDTO getCurrentTrip();

    List<TripDTO> getAllTrips();
}
