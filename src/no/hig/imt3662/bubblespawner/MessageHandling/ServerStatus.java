package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.Location;
import no.hig.imt3662.bubblespawner.MainEnvironment;
import no.hig.imt3662.bubblespawner.Node;
import no.hig.imt3662.bubblespawner.Serializing.ServerInfo;

import java.util.Map;
import java.util.logging.Level;

/**
 * Created by marti_000 on 14/09/24.
 */
public class ServerStatus implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        double latitude = Double.parseDouble((String) data.get("latitude"));
        double longitude = Double.parseDouble((String) data.get("longitude"));
        Location location = new Location(latitude, longitude);

        int userID = MainEnvironment.getNodeManager().getUserID(sender);
        if (userID == 0) { // Not registered yet
            userID = MainEnvironment.getNodeManager().registerNode(sender, location);
        }

        int userCount = MainEnvironment.getNodeManager().getUserCount(location, MainEnvironment.DEFAULT_RADIUS);

        ServerInfo response = new ServerInfo(userCount, userID);
        MainEnvironment.getCommunicationHandler().sendMessage(response, sender);
    }

    @Override
    public String getIdentifier() {
        return "GET_STATUS";
    }
}
