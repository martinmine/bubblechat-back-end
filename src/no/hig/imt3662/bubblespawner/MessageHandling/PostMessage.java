package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.Location;
import no.hig.imt3662.bubblespawner.MainEnvironment;
import no.hig.imt3662.bubblespawner.Node;
import no.hig.imt3662.bubblespawner.Serializing.ChatMessage;

import java.util.Map;

/**
 * Message handler for chat messages the user wants to distribute
 * Created by Martin on 14/09/24.
 */
public class PostMessage implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        String message = (String) data.get("message_text");
        double latitude = Double.parseDouble((String) data.get("latitude"));
        double longitude = Double.parseDouble((String) data.get("longitude"));
        boolean broadcastLocation = Boolean.parseBoolean((String) data.get("broadcast_location"));
        String username = (String) data.get("username");

        Location loc = new Location(latitude, longitude);
        Node node = MainEnvironment.getNodeManager().getNode(sender);
        if (node != null) {
            MainEnvironment.getNodeManager().updateNodeLocation(sender, loc);
            node.setLocation(loc);
            Location chatLocation = broadcastLocation ? node.getLocation() : Location.EMPTY;
            ChatMessage response = new ChatMessage(node.getId(), message, chatLocation, broadcastLocation, username);
            MainEnvironment.broadcastMessage(response, node.getLocation(), MainEnvironment.DEFAULT_RADIUS);
        }
    }

    @Override
    public String getIdentifier() {
        return "POST_MESSAGE";
    }
}
