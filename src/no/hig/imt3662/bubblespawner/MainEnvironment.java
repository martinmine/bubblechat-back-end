package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class MainEnvironment {
    public static final int DEFAULT_RADIUS = 30;
    private static Logger defaultLogger = Logger.getLogger("BubbleChatLog");

    public static final String GCM_ELEMENT_NAME = "gcm";
    public static final String GCM_NAMESPACE = "google:mobile:data";
    private static NodeManager nodeManager;
    private static Pinger pingManager;

    public static Logger getDefaultLogger() {
        return defaultLogger;
    }

    private static CommunicationHandler communicationHandler;
    private static DatabaseManager dbManager;

    public static CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public static DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public static void initialize() {
        getDefaultLogger().info("Initializing main environment");

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

/*
        communicationHandler = new CommunicationHandler(gcmServer, gcmPort);

        try {
            communicationHandler.connect(senderId, password);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }*/

        nodeManager = new NodeManager();
        pingManager = new Pinger(pingInterval);

        getDefaultLogger().info("Initialized");
    }

    public static long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static NodeManager getNodeManager() {
        return nodeManager;
    }

    public static int broadcastMessage(MessageResponse response, Location location, int radius) {
        List<Node> nodes = MainEnvironment.getNodeManager().getNodesNearby(location, radius);
        for (Node node : nodes) {
            getCommunicationHandler().sendMessage(response, node.getKey());
        }

        return nodes.size();
    }
}
