package kr.ac.jbnu.mobileAppProgramming.group10.database.dto;

import android.support.annotation.NonNull;

public class ScheduleDTO implements Comparable<ScheduleDTO> {
    private Integer schedule_id;
    private Integer schedule_trip_id;
    private String schedule_name;
    private Integer schedule_date_year;
    private Integer schedule_date_month;
    private Integer schedule_date_day;
    private Integer schedule_hour;
    private Integer schedule_minute;

    public ScheduleDTO() {}
    public ScheduleDTO(Integer schedule_trip_id, String schedule_name, Integer schedule_date_year, Integer schedule_date_month,
                       Integer schedule_date_day, Integer schedule_hour, Integer schedule_minute) {
        this.schedule_trip_id = schedule_trip_id;
        this.schedule_name = schedule_name;
        this.schedule_date_year = schedule_date_year;
        this.schedule_date_month = schedule_date_month;
        this.schedule_date_day = schedule_date_day;
        this.schedule_hour = schedule_hour;
        this.schedule_minute = schedule_minute;
    }

    public Integer getSchedule_id() { return schedule_id; }
    public Integer getSchedule_trip_id() { return schedule_trip_id; }
    public String getSchedule_name() { return schedule_name; }
    public Integer getSchedule_date_year() { return schedule_date_year; }
    public Integer getSchedule_date_month() { return schedule_date_month; }
    public Integer getSchedule_date_day() { return schedule_date_day; }
    public Integer getSchedule_hour() { return schedule_hour; }
    public Integer getSchedule_minute() { return schedule_minute; }

    public void setSchedule_id(Integer schedule_id) { this.schedule_id = schedule_id; }
    public void setSchedule_trip_id(Integer schedule_trip_id) { this.schedule_trip_id = schedule_trip_id;}
    public void setSchedule_name(String schedule_name) { this.schedule_name = schedule_name; }
    public void setSchedule_date_year(Integer schedule_date_year) { this.schedule_date_year = schedule_date_year; }
    public void setSchedule_date_month(Integer schedule_date_month) { this.schedule_date_month = schedule_date_month; }
    public void setSchedule_date_day(Integer schedule_date_day) { this.schedule_date_day = schedule_date_day; }
    public void setSchedule_hour(Integer schedule_hour) { this.schedule_hour = schedule_hour; }
    public void setSchedule_minute(Integer schedule_minute) { this.schedule_minute = schedule_minute; }


    @Override
    public int compareTo(@NonNull ScheduleDTO o) {
        if(this.schedule_hour > o.schedule_hour) {
            return 1;
        } else if(this.schedule_hour < o.schedule_hour) {
            return -1;
        } else {
            if(this.schedule_minute > o.schedule_minute) {
                return 1;
            } else if(this.schedule_minute < o.schedule_minute) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
