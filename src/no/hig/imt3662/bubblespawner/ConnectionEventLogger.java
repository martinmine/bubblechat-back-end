package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.logging.Logger;

/**
 * This class logs connection events between this program and gcm
 * Created by Martin on 14/09/26.
 */
public class ConnectionEventLogger implements ConnectionListener {
    private static final Logger LOGGER = Logger.getLogger(ConnectionEventLogger.class.getName());

    @Override
    public void connected(XMPPConnection connection) {
        LOGGER.info("Connected to gcm server");
    }

    @Override
    public void authenticated(XMPPConnection connection) {
        LOGGER.info("Authenticated");
    }

    @Override
    public void connectionClosed() {
        LOGGER.warning("Disconnected from gcm");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        LOGGER.severe("Failed to close connection: " + e.getMessage());
    }

    @Override
    public void reconnectingIn(int seconds) {
        LOGGER.info("Reconnecting in " + seconds + " seconds");
    }

    @Override
    public void reconnectionSuccessful() {
        LOGGER.info("Reconnected");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        LOGGER.severe("Failed to reconnect");
    }
}
