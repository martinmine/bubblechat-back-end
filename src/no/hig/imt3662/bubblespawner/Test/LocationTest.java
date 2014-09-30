package no.hig.imt3662.bubblespawner.Test;

import no.hig.imt3662.bubblespawner.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    private Location location;
    private static final double lat = 12.2d;
    private static final double lng = 34.43d;

    public LocationTest() {
        location = new Location(lat, lng);
    }

    @Test
    public void testGetLatitude() throws Exception {
        assertEquals(lat, location.getLatitude(), 0);
    }

    @Test
    public void testSetLatitude() throws Exception {
        location.setLatitude(23);
        assertEquals(23, location.getLatitude(), 0);
    }

    @Test
    public void testGetLongitude() throws Exception {
        assertEquals(lng, location.getLongitude(), 0);
    }

    @Test
    public void testSetLongitude() throws Exception {
        location.setLongitude(23);
        assertEquals(23, location.getLongitude(), 0);
    }
}