package no.hig.imt3662.bubblespawner;

/**
 * General wrapper for latitude and longitude (double)
 * Created by Martin on 14/09/25.
 */
public class Location {
    public static final Location EMPTY = new Location(0, 0);
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
