package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class MainEnvironment {
    private static final Logger LOGGER = Logger.getLogger(MainEnvironment.class.getName());

    public static final int DEFAULT_RADIUS = 30;
    public static final String GCM_ELEMENT_NAME = "gcm";
    public static final String GCM_NAMESPACE = "google:mobile:data";

    private static NodeManager nodeManager;
    private static Pinger pingManager;
    private static CommunicationHandler communicationHandler;
    private static DatabaseManager dbManager;

    public static CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public static DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public static NodeManager getNodeManager() {
        return nodeManager;
    }

    public static void initialize() {
        LOGGER.info("Initializing main environment");

        // TODO read this from config + MySQL
        final long senderId = 437017129818L; // your GCM sender id
        final String password = "AIzaSyDPt6s8tyrzqW5nE3Q6GiEjWzAJ_ev-lnM";
        final String gcmServer = "gcm.googleapis.com";
        final int gcmPort = 5235;
        final String dbUsername = "root";
        final String dbPassword = "lol123";
        final String dbName = "bubblechat";
        final String dbHostname = "localhost";
        final int dbPort = 3306;
        final long pingInterval = 30;

        dbManager = new DatabaseManager(dbUsername, dbPassword, dbName,dbHostname, dbPort);

        communicationHandler = new CommunicationHandler(gcmServer, gcmPort);

        try {
            communicationHandler.connect(senderId, password);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }

        nodeManager = new NodeManager();
        pingManager = new Pinger(pingInterval);

        LOGGER.info("Initialized");
    }

    public static long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static int broadcastMessage(MessageResponse response, Location location, int radius) {
        List<Node> nodes = MainEnvironment.getNodeManager().getNodesNearby(location, radius);
        LOGGER.info("Broadcasting msg " + response.getIdentifier() + " to " + nodes.size() + " nodes");
        for (Node node : nodes) {
            getCommunicationHandler().sendMessage(response, node.getKey());
        }

        return nodes.size();
    }
}
