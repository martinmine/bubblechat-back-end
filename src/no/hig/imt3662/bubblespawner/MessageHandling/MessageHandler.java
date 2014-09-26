package no.hig.imt3662.bubblespawner.MessageHandling;

import java.util.Map;

/**
 * General interface for message handlers.
 * Created by Martin on 14/09/24.
 */
public interface MessageHandler {
    /**
     * Makes the message handler handle the incoming message
     * @param data The incoming message (parsed JSON)
     * @param sender The ID of the sender from the JSON message
     */
    void invoke(Map<String, Object> data, String sender);
    String getIdentifier();
}
