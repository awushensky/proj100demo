package com.goproject100.demo.model;

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
    public Position(final String latitude, final String longitude) {
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
