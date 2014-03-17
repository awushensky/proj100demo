package com.goproject100.demo.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class represents a latitude and longitude position.
 */
public class Position {

    private final String latitude;
    private final String longitude;

    /**
     * Create a new position with provided lat and long.
     * @param latitude the latitude of the position
     * @param longitude the longitude of the position
     */
    @JsonCreator
    public Position(@JsonProperty("lat") final String latitude, @JsonProperty("long") final String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return the latitude of this position.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude of this position.
     */
    public String getLongitude() {
        return longitude;
    }
}
