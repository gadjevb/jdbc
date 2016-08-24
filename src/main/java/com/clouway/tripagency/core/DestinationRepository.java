package com.clouway.tripagency.core;

import java.sql.Date;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface DestinationRepository {

    void setEgn(Long egn);
    void setArrival(Date arrival);
    void setDeparting(Date departing);
    void setCityName(String cityName);
    Long getEgn();
    Date getArrival();
    Date getDeparting();
    String getCityName();

}
