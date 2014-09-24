package no.hig.imt3662.bubblespawner.MessageHandling;

import java.util.Map;

/**
 * Created by Martin on 14/09/24.
 */
public interface MessageHandler {
    void invoke(Map<String, Object> data, String sender);
    String getIdentifier();
}
