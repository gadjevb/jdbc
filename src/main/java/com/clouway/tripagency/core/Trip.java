package com.clouway.tripagency.core;

import java.sql.Date;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Trip {

    public final UID egn;
    public final Date arrival;
    public final Date departing;
    public final String cityName;

    public Trip(UID egn, Date arrival, Date departing, String cityName) {
        this.egn = egn;
        this.arrival = arrival;
        this.departing = departing;
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "egn=" + egn +
                ", arrival=" + arrival +
                ", departing=" + departing +
                ", cityName='" + cityName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;

        Trip that = (Trip) o;

        if (!egn.id.equals(that.egn.id)) return false;
        if (!arrival.equals(that.arrival)) return false;
        if (!departing.equals(that.departing)) return false;
        return cityName.equals(that.cityName);

    }
}
