package no.hig.imt3662.bubblespawner;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Responsible for keeping track of the nodes
 * Created by Martin on 14/09/25.
 */
public class NodeManager {
    private static final Logger LOGGER = Logger.getLogger(NodeManager.class.getName());

    /**
     * Gets all nodes within a given radius
     * @param location The location of where we want nodes from (center)
     * @param distance Length of the radius
     * @return A list of nodes that is within the radius
     */
    public List<Node> getNodesNearby(Location location, int distance) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                // Thanks to Google for this query:
                //  https://developers.google.com/maps/articles/phpsqlsearch_v3#findnearsql
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT "
                        + "  id, gcmKey, latitude, longitude, lastPinged, lastPingReceived, ( "
                        + "    3959 * 1609.344 * acos ( "
                        + "      cos(radians(?)) "
                        + "      * cos(radians(latitude)) "
                        + "      * cos(radians(longitude) - radians(?)) "
                        + "      + sin (radians(?)) "
                        + "      * sin(radians(latitude)) "
                        + "    ) "
                        + "  ) AS distance "
                        + "FROM Node "
                        + "HAVING distance < ?");
                stmt.setDouble(1, location.getLatitude());
                stmt.setDouble(2, location.getLongitude());
                stmt.setDouble(3, location.getLatitude());
                stmt.setInt(4, distance);

                ResultSet rs = stmt.executeQuery();

                List<Node> nodes = new LinkedList<Node>();
                while (rs.next()) {
                    nodes.add(getNodeFromRow(rs));
                }

                return nodes;
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }

        return null;
    }

    private Node getNodeFromRow(ResultSet rs) throws SQLException {
        return new Node(rs.getInt(1), rs.getString(2), new Location(rs.getDouble(3), rs.getDouble(4)),
                rs.getLong(5), rs.getLong(6));
    }

    /**
     * Updates a nodes location
     * @param key The gcm identifier from gcm
     * @param location Location of the node
     */
    public void updateNodeLocation(String key, Location location) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("UPDATE Node SET latitude = ?, longitude = ? "
                        + "WHERE gcmKey = ?");
                stmt.setDouble(1, location.getLatitude());
                stmt.setDouble(2, location.getLongitude());
                stmt.setString(3, key);
                stmt.executeUpdate();

            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }
    }

    /**
     * Registers a new node
     * @param key The gcm identifier from gcm
     * @param location Location of the node
     * @return user ID, incremented for each registration
     */
    public int registerNode(String key, Location location) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("INSERT INTO Node (gcmKey, latitude, longitude) "
                        + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, key);
                stmt.setDouble(2, location.getLatitude());
                stmt.setDouble(3, location.getLongitude());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                try {
                    if (rs.next()) {
                       return rs.getInt(1);
                    }
                }
                finally {
                    rs.close();
                }
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }

        return 0;
    }

    /**
     * Gets a node
     * @param key The gcm identifier from gcm
     * @return The node, null if it doesn't exist
     */
    public Node getNode(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT id, gcmKey, latitude, longitude, lastPinged, lastPingReceived "
                        + "FROM Node "
                        + "WHERE gcmKey = ?");
                stmt.setString(1, key);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return getNodeFromRow(rs);
                }

            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }

        return null;
    }

    /**
     * Sets the state of the node to pinged
     * @param key The gcm identifier from gcm
     */
    public void setNodePinged(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("UPDATE Node SET lastPingReceived = ? "
                        + "WHERE gcmKey = ?");
                stmt.setLong(1, MainEnvironment.getCurrentTimestamp());
                stmt.setString(2, key);
                stmt.executeUpdate();
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }
    }

    /**
     * Gets amount of users within an area
     * @param location Center coordinates
     * @param radius Radius of the area
     * @return Amount of users within our area
     */
    public int getNodeCount(Location location, int radius) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT "
                        + "  COUNT(*), ( "
                        + "    3959 * 1609.344 * acos ( "
                        + "      cos(radians(?)) "
                        + "      * cos(radians(latitude)) "
                        + "      * cos(radians(longitude) - radians(?)) "
                        + "      + sin (radians(?)) "
                        + "      * sin(radians(latitude)) "
                        + "    ) "
                        + "  ) AS distance "
                        + "FROM Node "
                        + "HAVING distance < ?");
                stmt.setDouble(1, location.getLatitude());
                stmt.setDouble(2, location.getLongitude());
                stmt.setDouble(3, location.getLatitude());
                stmt.setInt(4, radius);

                ResultSet rs = stmt.executeQuery();

                if (rs.next())
                    return rs.getInt(1);
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }

        return 0;
    }

    /**
     * Gets the ID of a node
     * @param key The gcm identifier from gcm
     * @return ID of the node, 0 if not exists
     */
    public int getNodeID(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT id "
                        + "FROM Node "
                        + "WHERE gcmKey = ?");
                stmt.setString(1, key);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }

        return 0;
    }

    /**
     * Gets all nodes that has not said anything since a given time stamp
     * @param timeout The last time stamp when a user has said anything
     * @return Idle nodes
     */
    public List<Node> findTimedOutNodes(long timeout){
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT id FROM node WHERE lastPingReceived < ?");
                stmt.setLong(1, timeout);

                ResultSet rs = stmt.executeQuery();

                List<Node> nodes = new LinkedList<Node>();
                while (rs.next()) {
                    nodes.add(getNodeFromRow(rs));
                }

                return nodes;
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }
        return null;
    }

    /**
     * Destroys a node
     * @param nodeID ID of the node to destroy
     */
    public void destroyNode(int nodeID) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("DELETE FROM Node WHERE id = ?");
                stmt.setInt(1, nodeID);
                stmt.executeUpdate();
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            LOGGER.severe("SQL error: " + ex.toString());
        }
    }
}
