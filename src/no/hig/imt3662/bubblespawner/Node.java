package no.hig.imt3662.bubblespawner;

/**
 * Created by Martin on 14/09/25.
 */
public class Node {
    private int id;
    private String key;
    private Location location;
    private long lastPinged;
    private long lastPingReceived;

    public Node(int id, String key, Location location, long lastPinged, long lastPingReceived) {
        this.id = id;
        this.key = key;
        this.location = location;
        this.lastPinged = lastPinged;
        this.lastPingReceived = lastPingReceived;
    }

    public Node(int id, String key, Location location) {
        this.id = id;
        this.key = key;
        this.location = location;

        this.lastPinged = this.lastPingReceived = MainEnvironment.getCurrentTimestamp();
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

    public long getLastPingReceived() {
        return lastPingReceived;
    }

    public void setLastPingReceived(long lastPingReceived) {
        this.lastPingReceived = lastPingReceived;
    }
}
