package no.hig.imt3662.bubblespawner.MessageHandling;

import java.util.Map;

/**
 * Created by marti_000 on 14/09/24.
 */
public class ServerStatus implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        double latitude = Double.parseDouble((String) data.get("latitude"));
        double longitude = Double.parseDouble((String) data.get("longitude"));
    }

    @Override
    public String getIdentifier() {
        return "GET_STATUS";
    }
}
