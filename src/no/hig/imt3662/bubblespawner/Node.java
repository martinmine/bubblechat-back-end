package no.hig.imt3662.bubblespawner;

/**
 * Holds information about one node
 * Created by Martin on 14/09/25.
 */
public class Node {
    private int id;
    private String key;
    private Location location;
    private long lastPinged;

    public Node(int id, String key, Location location, long lastPinged) {
        this.id = id;
        this.key = key;
        this.location = location;
        this.lastPinged = lastPinged;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getLastPinged() {
        return lastPinged;
    }

    public void setLastPinged(long lastPinged) {
        this.lastPinged = lastPinged;
    }
}
