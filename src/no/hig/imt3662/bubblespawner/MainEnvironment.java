package no.hig.imt3662.bubblespawner;

import java.util.logging.Logger;

/**
 * Created by Martin on 14/09/24.
 */
public class MainEnvironment {
    private static Logger defaultLogger = Logger.getLogger("SmackCcsClient");

    public static final String GCM_ELEMENT_NAME = "gcm";
    public static final String GCM_NAMESPACE = "google:mobile:data";

    public static Logger getDefaultLogger() {
        return defaultLogger;
    }
}
