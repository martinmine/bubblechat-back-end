package no.hig.imt3662.bubblespawner.MessageHandling;

import java.util.Map;

/**
 * Handles ack messages from gcm
 * Created by marti_000 on 14/09/24.
 */
public class AckHandler implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        /*int userID = MainEnvironment.getNodeManager().getNodeID(sender);
        if (userID > 0) {
            MainEnvironment.getNodeManager().setNodePinged(sender);
        }*/
    }

    @Override
    public String getIdentifier() {
        return "ack";
    }
}
