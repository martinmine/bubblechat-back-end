package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.logging.Level;

/**
 * Created by Martin on 14/09/24.
 */
final class LoggingConnectionListener
        implements ConnectionListener {

    @Override
    public void connected(XMPPConnection xmppConnection) {
        MainEnvironment.getDefaultLogger().info("Connected.");
    }

    @Override
    public void authenticated(XMPPConnection xmppConnection) {
        MainEnvironment.getDefaultLogger().info("Authenticated.");
    }

    @Override
    public void reconnectionSuccessful() {
        MainEnvironment.getDefaultLogger().info("Reconnecting..");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        MainEnvironment.getDefaultLogger().log(Level.INFO, "Reconnection failed.. ", e);
    }

    @Override
    public void reconnectingIn(int seconds) {
        MainEnvironment.getDefaultLogger().log(Level.INFO, "Reconnecting in %d secs", seconds);
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        MainEnvironment.getDefaultLogger().info("Connection closed on error.");
    }

    @Override
    public void connectionClosed() {
        MainEnvironment.getDefaultLogger().info("Connection closed.");
    }
}