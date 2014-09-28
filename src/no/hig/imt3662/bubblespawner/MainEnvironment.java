package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class MainEnvironment {
    private static final Logger LOGGER = Logger.getLogger(MainEnvironment.class.getName());

    public static final int DEFAULT_RADIUS = 100000;
    public static final String GCM_ELEMENT_NAME = "gcm";
    public static final String GCM_NAMESPACE = "google:mobile:data";

    private static NodeManager nodeManager;
    private static NodeVerifier pingManager;
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

        Properties properties = new Properties();

        try  {
            FileInputStream fs = new FileInputStream("server.properties");
            properties.load(fs);
        }
        catch (IOException e) {
            LOGGER.severe("Unable to load properties file");
        }

        final long senderId = Long.valueOf(properties.getProperty("gcm.senderID"));
        final String password = properties.getProperty("gcm.APIkey");
        final String gcmServer = properties.getProperty("gcm.hostname");
        final int gcmPort = Integer.valueOf(properties.getProperty("gcm.port"));
        final String dbUsername = properties.getProperty("db.username");
        final String dbPassword = properties.getProperty("db.password");
        final String dbName = properties.getProperty("db.databasename");
        final String dbHostname = properties.getProperty("db.hostname");
        final int dbPort = Integer.valueOf(properties.getProperty("db.port"));
        final long pingInterval = Integer.valueOf(properties.getProperty("checkInterval"));

        dbManager = new DatabaseManager(dbUsername, dbPassword, dbName, dbHostname, dbPort);

        communicationHandler = new CommunicationHandler(gcmServer, gcmPort);

        try {
            communicationHandler.connect(senderId, password);
        } catch (XMPPException | IOException | SmackException e) {
            e.printStackTrace();
        }

        nodeManager = new NodeManager();
        pingManager = new NodeVerifier(pingInterval);

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
