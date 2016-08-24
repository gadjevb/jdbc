package com.clouway.tripagency.core;

import java.sql.Date;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Destination implements DestinationRepository {

    private Long egn;
    private Date arrival;
    private Date departing;
    private String cityName;

    public Destination(Long egn, Date arrival, Date departing, String cityName) {
        this.egn = egn;
        this.arrival = arrival;
        this.departing = departing;
        this.cityName = cityName;
    }

    public Long getEgn() {
        return egn;
    }

    public Date getArrival() {
        return arrival;
    }

    public Date getDeparting() {
        return departing;
    }

    public String getCityName() {
        return cityName;
    }

    public void setEgn(Long egn) {
        this.egn = egn;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public void setDeparting(Date departing) {
        this.departing = departing;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "egn=" + egn +
                ", arrival=" + arrival +
                ", departing=" + departing +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
