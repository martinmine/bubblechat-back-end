package no.hig.imt3662.bubblespawner;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 14/09/25.
 */
public class NodeManager {
    public List<Node> getNodesNearby(Location location, int distance) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try { // TODO get within a distance
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT"
                        + "  id, gcmKey, latitude, longitude, lastPinged, lastPingReceived, ("
                        + "    3959 * 1609.344 * acos ("
                        + "      cos(radians(?))"
                        + "      * cos(radians(latitude))"
                        + "      * cos(radians(longitude) - radians(?))"
                        + "      + sin (radians(?))"
                        + "      * sin(radians(latitude))"
                        + "    )"
                        + "  ) AS distance"
                        + "FROM Node"
                        + "HAVING distance > ?");
                stmt.setDouble(1, location.getLatitude());
                stmt.setDouble(2, location.getLongitude());
                stmt.setInt(3, distance);

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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }

        return null;
    }

    private Node getNodeFromRow(ResultSet rs) throws SQLException {
        return new Node(rs.getInt(1), rs.getString(2), new Location(rs.getDouble(3), rs.getDouble(4)),
                rs.getLong(5), rs.getLong(6));
    }

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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }
    }

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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }

        return 0;
    }

    public Node getNode(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try { // TODO get within a distance
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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }

        return null;
    }

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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }
    }

    public void setNodePingSent(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try {
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("UPDATE Node SET lastPinged = ? "
                        + "WHERE gcmKey = ?");
                stmt.setLong(1, MainEnvironment.getCurrentTimestamp());
                stmt.setString(2, key);
                stmt.executeUpdate();
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }
    }

    public int getUserCount(Location location, int radius) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try { // TODO get within a distance
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT COUNT(*) "
                        + "FROM Node "
                        + "WHERE latitude = ? AND longitude = ?");
                stmt.setDouble(1, location.getLatitude());
                stmt.setDouble(2, location.getLongitude());

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(0);
                }
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }

        return 0;
    }

    public int getUserID(String key) {
        Connection con = null;
        PreparedStatement stmt;

        try {
            try { // TODO get within a distance
                con = MainEnvironment.getDatabaseManager().getConnection();
                stmt = con.prepareStatement("SELECT id "
                        + "FROM Node "
                        + "WHERE gcmKey = ?");
                stmt.setString(1, key);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(0);
                }
            } finally {
                if (con != null) con.close();
            }
        }
        catch (SQLException ex) {
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }

        return 0;
    }

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
            // TODO make own logger
            MainEnvironment.getDefaultLogger().severe("SQL insertion error: " + ex.toString());
        }
        return null;
    }
}
