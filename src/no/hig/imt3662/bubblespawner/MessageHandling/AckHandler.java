package no.hig.imt3662.bubblespawner.MessageHandling;

import no.hig.imt3662.bubblespawner.MainEnvironment;
import no.hig.imt3662.bubblespawner.Serializing.Ack;

import java.util.Map;

/**
 * Created by marti_000 on 14/09/24.
 */
public class AckHandler implements MessageHandler {
    @Override
    public void invoke(Map<String, Object> data, String sender) {
        String messageID = (String) data.get("from");
        Ack response = new Ack(messageID);
        MainEnvironment.getCommunicationHandler().sendMessage(response, sender);
    }

    @Override
    public String getIdentifier() {
        return "ack";
    }
}
