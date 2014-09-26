package no.hig.imt3662.bubblespawner;

import no.hig.imt3662.bubblespawner.MessageHandling.*;
import no.hig.imt3662.bubblespawner.Serializing.Ack;
import no.hig.imt3662.bubblespawner.Serializing.MessageResponse;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Packet;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class receives messages and delegates them to their proper message handler (MessageHandler)
 * Created by Martin on 14/09/24.
 */
public class MessageEventDispatcher implements PacketListener {
    private static final Logger LOGGER = Logger.getLogger(MessageEventDispatcher.class.getName());
    private Map<String, MessageHandler> messageEvents;

    /**
     * Creates a new message handler and registers all known messages
     * Note for further development: This could be done using reflection to make the code cleaner
     */
    public MessageEventDispatcher() {
        this.messageEvents = new HashMap<String, MessageHandler>();

        registerHandler(new AckHandler());
        registerHandler(new DestroyNode());
        registerHandler(new PostMessage());
        registerHandler(new ServerStatus());
    }

    private void registerHandler(MessageHandler handler) {
        messageEvents.put(handler.getIdentifier(), handler);
    }

    /**
     * Process a message from GCM and delegates it to the correct message handler
     * @param packet
     * @throws SmackException.NotConnectedException
     */
    @Override
    public void processPacket(Packet packet) throws SmackException.NotConnectedException {
        GcmPacketExtension gcmPacket = (GcmPacketExtension) packet.getExtension(MainEnvironment.GCM_NAMESPACE);

        try {
            Map<String, Object> messageValues = gcmPacket.getJsonValues();
            MessageHandler handler = null;
            String sender = (String)messageValues.get("from");

            Ack response = new Ack(sender);
            MainEnvironment.getCommunicationHandler().sendMessage(response, sender);

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
                LOGGER.info("Invoking handler for ID " + handler.getIdentifier());
                handler.invoke(messageValues, sender);
            }
            else {
                LOGGER.severe("Unknown or missing ID: " + packet.toString());
            }
        } catch (Exception e) {
            LOGGER.severe("Exception during parsing/handling: " + e.toString());
        }
    }
}
