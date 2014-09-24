package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.MessageHandling.AckHandler;
import no.hig.imt3662.bubblespawner.MessageHandling.MessageHandler;
import no.hig.imt3662.bubblespawner.Serializing.Ack;
import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Packet;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 14/09/24.
 */
public class MessageEventDispatcher implements PacketListener {
    private Map<String, MessageHandler> messageEvents;

    public MessageEventDispatcher() {
        this.messageEvents = new HashMap<String, MessageHandler>();

        registerHandler(new AckHandler());
    }

    private void registerHandler(MessageHandler handler) {
        messageEvents.put(handler.getIdentifier(), handler);
    }

    @Override
    public void processPacket(Packet packet) throws SmackException.NotConnectedException {
        GcmPacketExtension gcmPacket = (GcmPacketExtension) packet.getExtension(MainEnvironment.GCM_NAMESPACE);

        try {
            Map<String, Object> messageValues = gcmPacket.getJsonValues();
            MessageHandler handler = null;
            String sender = (String)messageValues.get("from");

            if (messageValues.containsKey("message_type")) { // Communication-level message
                String identifier = (String)messageValues.get("message_type");
                handler = this.messageEvents.get(identifier);
            }
            else if (messageValues.containsKey("data")) { // Application-level messages
                // Move down to the data object in the JSON message
                messageValues = (Map<String, Object>)messageValues.get("data");
                String identifier = (String)messageValues.get("id");
                handler = this.messageEvents.get(identifier);
            }

            // If this is a known message, invoke it
            if (handler != null) {
                MainEnvironment.getDefaultLogger().info("Invoking handler for ID " + handler.getIdentifier());
                handler.invoke(messageValues, sender);
            }
            else {
                MainEnvironment.getDefaultLogger().severe("Unknown or missing ID: " + packet.toString());
            }
        } catch (ParseException e) {
            MainEnvironment.getDefaultLogger().severe("Exception during parsing: " + e.toString());
        }
    }
}
