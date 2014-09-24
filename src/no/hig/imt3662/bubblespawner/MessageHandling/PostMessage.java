package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.MainEnvironment;

import java.util.Map;

/**
 * Created by marti_000 on 14/09/24.
 */
public class PostMessage implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        String message = (String) data.get("message_text");
        double latitude = Double.parseDouble((String) data.get("latitude"));
        double longitude = Double.parseDouble((String) data.get("longitude"));
        boolean broadcastLocation = Boolean.parseBoolean((String) data.get("broadcast_location"));

        MainEnvironment.getDefaultLogger().info("Posting message " + message+ "," + latitude + "," + longitude + " gps" + broadcastLocation);
    }

    @Override
    public String getIdentifier() {
        return "POST_MESSAGE";
    }
}
