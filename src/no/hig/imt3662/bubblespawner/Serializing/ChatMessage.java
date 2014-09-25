package no.hig.imt3662.bubblespawner.Serializing;

import no.hig.imt3662.bubblespawner.Location;

/**
 * Created by Martin on 14/09/24.
 */
public class ChatMessage extends MessageResponse {
    public ChatMessage(int userID, String message, Location location, boolean broadcastLocation, String username) {
        super();

        setValue("user_id", userID);
        setValue("message_text", message);
        setValue("has_location", broadcastLocation);
        setValue("latitude", broadcastLocation ? location.getLatitude() : 0);
        setValue("longitude", broadcastLocation ? location.getLongitude() : 0);
        setValue("username", username);
    }

    @Override
    public String getIdentifier() {
        return "BROADCAST_MESSAGE";
    }
}
