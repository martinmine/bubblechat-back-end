package no.hig.imt3662.bubblespawner;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class MainEnvironment {
    private static Logger defaultLogger = Logger.getLogger("BubbleChatLog");

    public static final String GCM_ELEMENT_NAME = "gcm";
    public static final String GCM_NAMESPACE = "google:mobile:data";

    public static Logger getDefaultLogger() {
        return defaultLogger;
    }

    private static CommunicationHandler communicationHandler;

    public static CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public static void initialize() {
        getDefaultLogger().info("Initializing main environment");

        // TODO read this from config + MySQL
        final long senderId = 437017129818L; // your GCM sender id
        final String password = "AIzaSyDPt6s8tyrzqW5nE3Q6GiEjWzAJ_ev-lnM";
        final String gcmServer = "gcm.googleapis.com";
        final int gcmPort = 5235;

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

        getDefaultLogger().info("Initialized");
    }
}
