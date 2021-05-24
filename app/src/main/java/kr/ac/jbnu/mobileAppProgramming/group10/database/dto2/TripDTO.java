package kr.ac.jbnu.mobileAppProgramming.group10.database.dto2;

public class TripDTO {
    private Integer trip_id;
    private String trip_name;
    private String trip_location;
    private Integer trip_start_date_year;
    private Integer trip_start_date_month;
    private Integer trip_start_date_day;
    private Integer trip_end_date_year;
    private Integer trip_end_date_month;
    private Integer trip_end_date_day;
    private Integer trip_is_current;

    public TripDTO(){}

    public TripDTO(String trip_name, String trip_location, Integer trip_start_date_year, Integer trip_start_date_month,
                   Integer trip_start_date_day, Integer trip_end_date_year, Integer trip_end_date_month, Integer trip_end_date_day, Integer trip_is_current){
        this.trip_name = trip_name;
        this.trip_location = trip_location;
        this.trip_start_date_year = trip_start_date_year;
        this.trip_start_date_month = trip_start_date_month;
        this.trip_start_date_day = trip_start_date_day;
        this.trip_end_date_year = trip_end_date_year;
        this.trip_end_date_month = trip_end_date_month;
        this.trip_end_date_day = trip_end_date_day;
        this.trip_is_current = trip_is_current;
    }

    public Integer getTrip_id() { return trip_id; }
    public String getTrip_name() { return trip_name; }
    public String getTrip_location() { return trip_location; }
    public Integer getTrip_start_date_year() {return trip_start_date_year;}
    public Integer getTrip_start_date_month() { return trip_start_date_month; }
    public Integer getTrip_start_date_day() { return trip_start_date_day; }
    public Integer getTrip_end_date_year() { return trip_end_date_year; }
    public Integer getTrip_end_date_month() { return trip_end_date_month; }
    public Integer getTrip_end_date_day() { return trip_end_date_day; }
    public Integer getTrip_is_current() { return trip_is_current; }

    public void setTrip_id(Integer trip_id) { this.trip_id = trip_id; }
    public void setTrip_name(String trip_name) { this.trip_name = trip_name; }
    public void setTrip_location(String trip_location) {this.trip_location = trip_location;}
    public void setTrip_start_date_year(Integer trip_start_date_year) { this.trip_start_date_year = trip_start_date_year; }
    public void setTrip_start_date_month(Integer trip_start_date_month) { this.trip_start_date_month = trip_start_date_month;}
    public void setTrip_start_date_day(Integer trip_start_date_day) { this.trip_start_date_day = trip_start_date_day; }
    public void setTrip_end_date_year(Integer trip_end_date_year) { this.trip_end_date_year = trip_end_date_year; }
    public void setTrip_end_date_month(Integer trip_end_date_month) { this.trip_end_date_month = trip_end_date_month; }
    public void setTrip_end_date_day(Integer trip_end_date_day) { this.trip_end_date_day = trip_end_date_day; }
    public void setTrip_is_current(Integer trip_is_current) { this.trip_is_current = trip_is_current; }
}
